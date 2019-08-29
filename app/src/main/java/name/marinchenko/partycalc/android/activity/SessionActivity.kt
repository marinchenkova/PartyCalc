package name.marinchenko.partycalc.android.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_session.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.base.WorkActivity
import name.marinchenko.partycalc.android.recycler.adapter.SessionAdapter
import name.marinchenko.partycalc.android.storage.session.SESSION_ID
import name.marinchenko.partycalc.android.storage.session.SessionRepo
import name.marinchenko.partycalc.android.recycler.ItemTouchListener
import name.marinchenko.partycalc.android.recycler.adapter.base.IdItemAdapter
import name.marinchenko.partycalc.android.storage.session.Session
import name.marinchenko.partycalc.android.util.setVisible
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class SessionActivity : WorkActivity() {

    override val baseLayout: View get() = base_layout
    override val sessionRepo: SessionRepo get() = SessionRepo(this)

    private lateinit var sessionAdapter: SessionAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

        initToolbar(getString(R.string.app_name), false)
        initRecyclerViews()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initRecyclerViews() {
        initLayoutManagers()
        initAdapters()
        initItemTouchHelpers()
        initButtons()
    }

    private fun initLayoutManagers() {
        list_sessions.layoutManager = LinearLayoutManager(this)
    }

    private fun initAdapters() {
        sessionAdapter = SessionAdapter(this)
                .onItemClick { item, _ ->
                    startActivity<MainActivity>(SESSION_ID to item.id)
                }
                as SessionAdapter

        sessionAdapter.callback = object : IdItemAdapter.Callback<Session> {
            override fun onAddItem(item: Session, position: Int, undoRemove: Boolean) {
                doAsync { sessionRepo.saveSession(item) }
            }
            override fun onRemoveItem(item: Session, position: Int) {
                doAsync { sessionRepo.removeSession(item.id) }
            }
            override fun onEditItem(item: Session, position: Int) {
                doAsync { sessionRepo.saveSession(item) }
            }
            override fun onUpdateList(new: List<Session>) {
                showNoSessions(new.isEmpty())
            }
        }

        list_sessions.adapter = sessionAdapter
    }

    private fun initItemTouchHelpers() {
        val sessionTouchHelper = ItemTouchHelper(ItemTouchListener()
                .onSwipeAction { holder, _ ->
                    sessionAdapter.removeItem(holder?.adapterPosition)
                    showUndoSnackBar(R.string.session_removed) { sessionAdapter.undoRemoveItem() }
                }
        )
        sessionTouchHelper.attachToRecyclerView(list_sessions)
    }

    private fun initButtons() {
        add_session_button.setOnClickListener {
            sessionAdapter.addItem(sessionRepo.createEmptySession())
        }
    }

    private fun initData() {
        doAsync {
            val sessions = sessionRepo.getAllSessions()
            uiThread {
                sessionAdapter.load(sessions)
            }
        }
    }

    private fun showNoSessions(show: Boolean) {
        no_sessions?.setVisible(show)
    }
}

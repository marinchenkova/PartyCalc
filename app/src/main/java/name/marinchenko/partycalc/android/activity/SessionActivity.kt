package name.marinchenko.partycalc.android.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_session.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.base.WorkActivity
import name.marinchenko.partycalc.android.adapter.SessionAdapter
import name.marinchenko.partycalc.android.storage.SESSION_ID
import name.marinchenko.partycalc.android.storage.SessionRepo
import name.marinchenko.partycalc.android.util.listener.ItemTouchListener
import name.marinchenko.partycalc.android.util.setVisible
import org.jetbrains.anko.startActivity

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
                .onItemAdd { sessionRepo.saveSession(it) }
                .onItemEdit { sessionRepo.saveSession(it) }
                .onItemRemove { sessionRepo.removeSession(it.id) }
                .onListChanged { showNoSessions(it.isEmpty()) }
                .onItemClick { item, _ ->
                    startActivity<MainActivity>(SESSION_ID to item.id)
                }
                as SessionAdapter

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
        sessionAdapter.update(sessionRepo.getAllSessions())
    }

    private fun showNoSessions(show: Boolean) {
        no_sessions?.setVisible(show)
    }
}

package name.marinchenko.partycalc.android.activity


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.base.WorkActivity
import name.marinchenko.partycalc.android.recycler.adapter.PayerAdapter
import name.marinchenko.partycalc.android.recycler.adapter.ProductAdapter
import name.marinchenko.partycalc.android.recycler.adapter.ResultAdapter
import name.marinchenko.partycalc.android.recycler.adapter.base.IdItemAdapter
import name.marinchenko.partycalc.android.recycler.listener.ItemTouchListener
import name.marinchenko.partycalc.android.recycler.viewHolder.SummaryViewHolder
import name.marinchenko.partycalc.android.storage.getEasterActivated
import name.marinchenko.partycalc.android.storage.getShareIncludePayers
import name.marinchenko.partycalc.android.storage.getShareIncludeProducts
import name.marinchenko.partycalc.android.storage.session.SESSION_ID
import name.marinchenko.partycalc.android.storage.session.Session
import name.marinchenko.partycalc.android.storage.session.SessionRepo
import name.marinchenko.partycalc.android.storage.setEasterActivated
import name.marinchenko.partycalc.android.util.Loader
import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread


class MainActivity : WorkActivity() {

    override val baseLayout: View get() = base_layout
    override val sessionRepo: SessionRepo get() = SessionRepo(this)
    private lateinit var session: Session

    private lateinit var summaryHolder: SummaryViewHolder
    private lateinit var productAdapter: ProductAdapter
    private lateinit var payerAdapter: PayerAdapter
    private lateinit var resultAdapter: ResultAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadSession()

        initToolbar(session.getAvailableTitle())
        initRecyclerViews()
        initSummaryHolder()

        initData()
    }

    override fun onResume() {
        super.onResume()
        checkShowHints()
    }

    private fun checkShowHints() {
        Log.d("checkShowHints", "")
        doAsync {
            session.checkShowHints(this@MainActivity)
            sessionRepo.saveSession(session)
        }
        productAdapter.checkShowHints()
        payerAdapter.checkShowHints()
        resultAdapter.checkShowHints()
    }

    private fun loadSession() {
        val id = intent.getLongExtra(SESSION_ID, 0)
        session = sessionRepo.getSession(id) ?: sessionRepo.createEmptySession()
    }

    private fun initRecyclerViews() {
        initLayoutManagers()
        initAdapters()
        initEaster()
        initItemTouchHelpers()
        initButtons()
    }

    private fun initLayoutManagers() {
        list_products.layoutManager = LinearLayoutManager(this)
        list_payers.layoutManager = LinearLayoutManager(this)
        list_results.layoutManager = LinearLayoutManager(this)
    }

    private fun initAdapters() {
        productAdapter = ProductAdapter(this)
        payerAdapter = PayerAdapter(this)

        productAdapter.callback = object : IdItemAdapter.Callback<Product> {
            override fun onAddItem(item: Product, position: Int, undoRemove: Boolean) {
                payerAdapter.productCallback.onAddItem(item, position, undoRemove)
            }
            override fun onRemoveItem(item: Product, position: Int) {
                payerAdapter.productCallback.onRemoveItem(item, position)
            }
            override fun onEditItem(item: Product, position: Int) {
                payerAdapter.productCallback.onEditItem(item, position)
            }
            override fun onUpdateList(new: List<Product>) {
                payerAdapter.productCallback.onUpdateList(new)
                doAsync {
                    sessionRepo.saveSession(session.also {
                        it.products = productAdapter.getItems()
                        it.payers = payerAdapter.getItems()
                    })
                }
            }
        }

        payerAdapter.callback = object : IdItemAdapter.Callback<Payer> {
            override fun onAddItem(item: Payer, position: Int, undoRemove: Boolean) {
                summaryHolder.update(payerAdapter.getItems(), productAdapter.getItems())
            }
            override fun onRemoveItem(item: Payer, position: Int) {
                summaryHolder.update(payerAdapter.getItems(), productAdapter.getItems())
            }
            override fun onEditItem(item: Payer, position: Int) {
                summaryHolder.update(payerAdapter.getItems(), productAdapter.getItems())
            }
            override fun onUpdateList(new: List<Payer>) {
                summaryHolder.update(payerAdapter.getItems(), productAdapter.getItems())
            }
        }

        payerAdapter
                .onLoadProducts { productAdapter.getItems() }
                .onExpandAction { _, _ ->
                    doAsync {
                        sessionRepo.saveSession(session.also {
                            it.payers = payerAdapter.getItems()
                        })
                    }
                }

        resultAdapter = ResultAdapter(this).onDoneAction { results ->
            doAsync {
                sessionRepo.saveSession(session.also { it.results = results })
            }
        }

        list_products.adapter = productAdapter
        list_payers.adapter = payerAdapter
        list_results.adapter = resultAdapter
    }

    private fun initEaster() {
        if (getEasterActivated()) return

        val loaderOne = Loader(1, true, productAdapter, payerAdapter) { easterOne() }
        val loaderBoth = Loader(2, true, productAdapter, payerAdapter) { easterBoth() }

        productAdapter.onEaster { ok ->
            loaderOne.loaded(productAdapter, ok)
            loaderBoth.loaded(productAdapter, ok)
        }
        payerAdapter.onEaster { ok ->
            loaderOne.loaded(payerAdapter, ok)
            loaderBoth.loaded(payerAdapter, ok)
        }
    }

    private fun easterOne() {
        longToast(R.string.easter_one)
    }

    private fun easterBoth() {
        setEasterActivated(true)
        longToast(R.string.easter_both)
    }

    private fun initItemTouchHelpers() {
        val productTouchHelper = ItemTouchHelper(ItemTouchListener()
                .onSwipeAction { holder, _ ->
                    productAdapter.removeItem(holder?.adapterPosition)
                    showUndoSnackBar(R.string.product_removed) { productAdapter.undoRemoveItem() }
                }
        )

        val payerTouchHelper = ItemTouchHelper(ItemTouchListener()
                .onSwipeAction { holder, _ ->
                    payerAdapter.removeItem(holder?.adapterPosition)
                    showUndoSnackBar(R.string.payer_removed) { payerAdapter.undoRemoveItem() }
                }
        )

        productTouchHelper.attachToRecyclerView(list_products)
        payerTouchHelper.attachToRecyclerView(list_payers)
    }

    private fun initButtons() {
        add_product_button.setOnClickListener { productAdapter.newItem() }
        add_payer_button.setOnClickListener { payerAdapter.newItem() }
    }

    private fun initSummaryHolder() {
        summaryHolder = SummaryViewHolder(this).onSumEqualityAction { results ->
            resultAdapter.load(results)

            doAsync {
                sessionRepo.saveSession(session.also {
                    it.products = productAdapter.getItems()
                    it.payers = payerAdapter.getItems()
                    it.results = results
                })
            }
        }
    }

    private fun initData() {
        progressbar.visibility = View.VISIBLE
        val loader = Loader(
                4,
                true,
                productAdapter, payerAdapter, resultAdapter, summaryHolder
        ) {
            progressbar.visibility = View.INVISIBLE
        }

        productAdapter.onLoad {
            list_products.scheduleLayoutAnimation()
            loader.loaded(productAdapter)
        }
        payerAdapter.onLoad {
            list_payers.scheduleLayoutAnimation()
            loader.loaded(payerAdapter)
        }
        resultAdapter.onLoad {
            list_results.scheduleLayoutAnimation()
            loader.loaded(resultAdapter)
        }
        summaryHolder.onLoad { loader.loaded(summaryHolder) }

        Handler().postDelayed({
            productAdapter.load(session.products)
            payerAdapter.load(session.payers)
            resultAdapter.load(session.results)
            summaryHolder.load(session.payers, session.products)
        }, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.toolbar_menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.toolbar_share -> {
            share()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun share() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getPartyText())
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, getString(R.string.toolbar_share)))
    }

    private fun getPartyText() = PartyCalc.TextBuilder()
            .title(session.getAvailableTitle())
            .products(productAdapter.getItems(), getShareIncludeProducts())
            .payers(payerAdapter.getItems(), getShareIncludePayers())
            .results(resultAdapter.getItems())
            .build()

}

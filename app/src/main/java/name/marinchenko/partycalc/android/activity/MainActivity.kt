package name.marinchenko.partycalc.android.activity


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.base.WorkActivity
import name.marinchenko.partycalc.android.adapter.PayerAdapter
import name.marinchenko.partycalc.android.adapter.ProductAdapter
import name.marinchenko.partycalc.android.adapter.ResultAdapter
import name.marinchenko.partycalc.android.storage.*
import name.marinchenko.partycalc.android.util.listener.ItemTouchListener
import name.marinchenko.partycalc.android.viewHolder.SummaryViewHolder
import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.item.Result
import org.jetbrains.anko.doAsync


class MainActivity : WorkActivity() {

    override val baseLayout: View get() = base_layout
    override val sessionRepo: SessionRepo get() = SessionRepo(this)
    private lateinit var session: Session

    private lateinit var summaryHolder: SummaryViewHolder
    private lateinit var productAdapter: ProductAdapter
    private lateinit var payerAdapter: PayerAdapter
    private lateinit var resultAdapter: ResultAdapter
    private var loadCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadSession()

        initToolbar(session.getAvailableTitle())
        initRecyclerViews()
        initSummaryHolder()
        initData()
    }

    private fun loadSession() {
        val id = intent.getLongExtra(SESSION_ID, 0)
        session = sessionRepo.getSession(id) ?: sessionRepo.createEmptySession()
    }

    private fun initRecyclerViews() {
        initLayoutManagers()
        initAdapters()
        initItemTouchHelpers()
        initButtons()
    }

    private fun initLayoutManagers() {
        list_products.layoutManager = LinearLayoutManager(this)
        list_payers.layoutManager = LinearLayoutManager(this)
        list_results.layoutManager = LinearLayoutManager(this)
    }

    private fun initAdapters() {
        productAdapter = ProductAdapter(this).onListChanged { products ->
            payerAdapter.productsWereUpdated(products)
        } as ProductAdapter

        payerAdapter = PayerAdapter(this).onListChanged { payers ->
            summaryHolder.update(payers, productAdapter.getItems())
        } as PayerAdapter

        resultAdapter = ResultAdapter(this).onDoneAction { results ->
            sessionRepo.saveSession(session.also { it.results = results })
        }

        list_products.adapter = productAdapter
        list_payers.adapter = payerAdapter
        list_results.adapter = resultAdapter
    }

    private fun initItemTouchHelpers() {
        val productTouchHelper = ItemTouchHelper(ItemTouchListener()
                .onMoveAction { _, holder, target ->
                    productAdapter.moveItem(holder?.adapterPosition, target?.adapterPosition)
                }
                .onSwipeAction { holder, _ ->
                    productAdapter.removeItem(holder?.adapterPosition)
                    showUndoSnackBar(productAdapter, R.string.product_removed)
                }
        )

        val payerTouchHelper = ItemTouchHelper(ItemTouchListener()
                .onMoveAction { _, holder, target ->
                    payerAdapter.moveItem(holder?.adapterPosition, target?.adapterPosition)
                }
                .onSwipeAction { holder, _ ->
                    payerAdapter.removeItem(holder?.adapterPosition)
                    showUndoSnackBar(payerAdapter, R.string.payer_removed)
                }
        )

        productAdapter.onItemDrag { holder -> productTouchHelper.startDrag(holder) }
        payerAdapter.onItemDrag { holder -> payerTouchHelper.startDrag(holder) }

        productTouchHelper.attachToRecyclerView(list_products)
        payerTouchHelper.attachToRecyclerView(list_payers)
    }

    private fun initButtons() {
        add_product_button.setOnClickListener { productAdapter.newItem() }
        add_payer_button.setOnClickListener { payerAdapter.newItem() }
    }

    private fun initSummaryHolder() {
        summaryHolder = SummaryViewHolder(this).onSumEqualityAction { produced ->
            val results = if (!canSaveSession()) session.results else produced
            resultAdapter.update(results)

            if (canSaveSession()) sessionRepo.saveSession(session.also {
                it.products = productAdapter.getItems()
                it.payers = payerAdapter.getItems()
                it.results = results
            })

            loadCount++
        }
    }

    private fun initData() {
        productAdapter.update(session.products)
        payerAdapter.update(session.payers)
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
            .title(session.title)
            .products(productAdapter.getItems(), getShareIncludeProducts())
            .payers(payerAdapter.getItems(), getShareIncludePayers())
            .results(resultAdapter.getItems(), getShareIncludeResults())
            .build()

    private fun canSaveSession() = loadCount >= 2


}

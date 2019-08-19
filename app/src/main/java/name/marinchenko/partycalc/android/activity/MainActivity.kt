package name.marinchenko.partycalc.android.activity


import android.content.ClipData
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.PayerAdapter
import name.marinchenko.partycalc.android.adapter.ProductAdapter
import name.marinchenko.partycalc.android.adapter.ResultAdapter
import name.marinchenko.partycalc.android.adapter.base.UndoRemoveAdapter
import name.marinchenko.partycalc.android.session.Session
import name.marinchenko.partycalc.android.session.SessionId
import name.marinchenko.partycalc.android.session.SessionRepo
import name.marinchenko.partycalc.android.util.listener.ItemTouchListener
import name.marinchenko.partycalc.android.viewHolder.SummaryViewHolder
import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.textPayers
import name.marinchenko.partycalc.core.textProducts
import name.marinchenko.partycalc.core.textResults
import org.jetbrains.anko.clipboardManager
import org.jetbrains.anko.toast


class MainActivity : ToolbarActivity() {

    override val baseLayout: View get() = base_layout

    private val sessionRepo = SessionRepo()
    private lateinit var session: Session

    private lateinit var summaryHolder: SummaryViewHolder
    private lateinit var productAdapter: ProductAdapter
    private lateinit var payerAdapter: PayerAdapter
    private lateinit var resultAdapter: ResultAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        session = sessionRepo.getSession(SessionId())

        initToolbar(session.title)
        initRecyclerViews()
        initSummary()
        initData()
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
        productAdapter = ProductAdapter(this).onListChanged {list ->
            payerAdapter.productsWereUpdated(list)
            summaryHolder.productsUpdated(list)
        } as ProductAdapter

        payerAdapter = PayerAdapter(this).onListChanged { list ->
            summaryHolder.payersUpdated(list)
        } as PayerAdapter

        resultAdapter = ResultAdapter(this)

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

    private fun showUndoSnackBar(adapter: UndoRemoveAdapter, @StringRes what: Int) {
        Snackbar.make(base_layout, what, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo) { adapter.undoRemoveItem() }
                .show()
    }

    private fun initSummary() {
        summaryHolder = SummaryViewHolder(this)
                .onSumEqualityAction { results ->
                    resultAdapter.updateList(results)
                }
                //.onAddDiffPayer { sum -> payerAdapter.newItem(sum) }
    }

    private fun initData() {
        productAdapter.update(session.products)
        payerAdapter.update(session.payers)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.toolbar_clipboard_copy -> {
            copyToClipBoard()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun copyToClipBoard() {
        val text = PartyCalc.TextBuilder()
                .title(session.title)
                .products(productAdapter.getItems())
                .payers(payerAdapter.getItems())
                .results(resultAdapter.getItems())
                .build()

        clipboardManager.primaryClip = ClipData.newPlainText(session.label, text)
        toast(R.string.toolbar_copied)
    }

}

package name.marinchenko.partycalc.android.activity


import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.PayerAdapter
import name.marinchenko.partycalc.android.adapter.ProductAdapter
import name.marinchenko.partycalc.android.adapter.ResultAdapter
import name.marinchenko.partycalc.android.adapter.base.UndoRemoveAdapter
import name.marinchenko.partycalc.android.util.listener.ItemTouchListener
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.android.util.spanResult
import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product
import name.marinchenko.partycalc.core.item.Result


class MainActivity : ToolbarActivity() {

    override val baseLayout: View get() = base_layout

    private lateinit var productAdapter: ProductAdapter
    private lateinit var payerAdapter: PayerAdapter
    private lateinit var resultAdapter: ResultAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar("Session")
        initLists()
        initResults()
    }

    private fun initLists() {
        initLayoutManagers()
        initAdapters()
        initItemTouchHelpers()
        initButtons()
    }

    private fun productSummary(products: List<Product>) {
        val size = products.size.toString()
        val sum = PartyCalc.itemListSum(products)
        result_products?.text = spanResult(
                size,
                getString(R.string.result_products),
                sum,
                R.color.colorPrimaryDark
        )
    }

    private fun payerSummary(payers: List<Payer>) {
        val size = payers.size.toString()
        val sum = PartyCalc.itemListSum(payers)
        result_payers?.text = spanResult(
                size,
                getString(R.string.result_payers),
                sum,
                R.color.colorPrimaryDark
        )
    }

    private fun initResults() {
        productSummary(emptyList())
        payerSummary(emptyList())
        resultAdapter.updateList(setOf(
                Result(
                        Payer(0, "Samuel L. Jackson", "000", 0),
                        Payer(1, "Quentin Tarantino", "111", 1),
                        "666"
                ),
                Result(
                        Payer(2, "fred", "222", 2),
                        Payer(3, "bob", "333", 3),
                        "777"
                )
        ))
    }

    private fun initAdapters() {
        productAdapter = ProductAdapter(this, null,
                object : SimpleEventListener<List<Product>>{
                    override fun onEvent(item: List<Product>) {
                        payerAdapter.productsWereUpdated(item)
                        productSummary(item)
                    }
                }
        )
        payerAdapter = PayerAdapter(this, object : SimpleEventListener<List<Payer>>{
            override fun onEvent(item: List<Payer>) {
                payerSummary(item)
            }
        })
        resultAdapter = ResultAdapter(this)

        list_products.adapter = productAdapter
        list_payers.adapter = payerAdapter
        list_results.adapter = resultAdapter
    }

    private fun initLayoutManagers() {
        list_products.layoutManager = LinearLayoutManager(this)
        list_payers.layoutManager = LinearLayoutManager(this)
        list_results.layoutManager = LinearLayoutManager(this)
    }

    private fun initItemTouchHelpers() {
        val productTouchHelper = ItemTouchHelper(ItemTouchListener(
                { _, holder, target ->
                    productAdapter.moveItem(holder?.adapterPosition, target?.adapterPosition)
                },
                { holder, _ ->
                    val pos = holder?.adapterPosition
                    productAdapter.removeItem(pos)
                    showUndoSnackBar(productAdapter, R.string.product_removed)
                }
        ))

        val payerTouchHelper = ItemTouchHelper(ItemTouchListener(
                { _, holder, target ->
                    payerAdapter.moveItem(holder?.adapterPosition, target?.adapterPosition)
                },
                { holder, _ ->
                    val pos = holder?.adapterPosition
                    payerAdapter.removeItem(pos)
                    showUndoSnackBar(payerAdapter, R.string.payer_removed)
                }
        ))

        productTouchHelper.attachToRecyclerView(list_products)
        payerTouchHelper.attachToRecyclerView(list_payers)
    }

    private fun showUndoSnackBar(adapter: UndoRemoveAdapter, @StringRes what: Int) {
        Snackbar.make(base_layout, what, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo) { adapter.undoRemoveItem() }
                .show()
    }

    private fun initButtons() {
        add_product_button.setOnClickListener { productAdapter.newItem() }
        add_payer_button.setOnClickListener { payerAdapter.newItem() }
    }
}

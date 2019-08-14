package name.marinchenko.partycalc.android.activities


import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.adapter.ItemRecyclerAdapter
import name.marinchenko.partycalc.android.util.adapter.PayerRecyclerAdapter
import name.marinchenko.partycalc.android.util.adapter.ProductRecyclerAdapter
import name.marinchenko.partycalc.android.util.adapter.ResultRecyclerAdapter
import name.marinchenko.partycalc.android.util.listener.ItemTouchListener
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.core.item.Item
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Result
import org.jetbrains.anko.toast


class MainActivity : ToolbarActivity() {

    override val baseLayout: View get() = base_layout

    private lateinit var productAdapter: ProductRecyclerAdapter
    private lateinit var payerAdapter: PayerRecyclerAdapter
    private lateinit var resultAdapter: ResultRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar("Session")
        initLists()
        initData()
    }

    private fun initLists() {
        initLayoutManagers()
        initAdapters()
        initItemTouchHelpers()
        initButtons()
    }

    private fun initData() {
        resultAdapter.addResult(Result(
                Payer(0,"", "", 0),
                Payer(0,"", "", 0),
                0
        ))
    }

    private fun initAdapters() {
        productAdapter = ProductRecyclerAdapter(this, object : OnItemClickListener {
            override fun onItemClick(item: Item) {
                //productAdapter.removeItem(item)
            }
        })

        payerAdapter = PayerRecyclerAdapter(this, object : OnItemClickListener {
            override fun onItemClick(item: Item) {
                //productAdapter.removeItem(item)
            }
        })

        resultAdapter = ResultRecyclerAdapter(layoutInflater)

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

    private fun showUndoSnackBar(itemAdapter: ItemRecyclerAdapter<*>, @StringRes what: Int) {
        Snackbar.make(base_layout, what, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo) { itemAdapter.undoRemoveItem() }
                .show()
    }

    private fun initButtons() {
        add_product_button.setOnClickListener { productAdapter.newItem() }
        add_payer_button.setOnClickListener { payerAdapter.newItem() }
    }
}

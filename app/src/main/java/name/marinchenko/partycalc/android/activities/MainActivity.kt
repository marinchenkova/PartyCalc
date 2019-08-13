package name.marinchenko.partycalc.android.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.ItemFactory
import name.marinchenko.partycalc.android.util.adapter.PayerRecyclerAdapter
import name.marinchenko.partycalc.android.util.adapter.ProductRecyclerAdapter
import name.marinchenko.partycalc.android.util.adapter.ResultRecyclerAdapter
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.listener.SwipeListener
import name.marinchenko.partycalc.core.item.Item
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Result


class MainActivity : AppCompatActivity() {

    private val factory = ItemFactory(this)
    private lateinit var productAdapter: ProductRecyclerAdapter
    private lateinit var payerAdapter: PayerRecyclerAdapter
    private lateinit var resultAdapter: ResultRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLists()
        initData()
    }

    private fun initLists() {
        initLayoutManagers()
        initAdapters()
        initSwipeToDelete()
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
        productAdapter = ProductRecyclerAdapter(layoutInflater, object : OnItemClickListener {
            override fun onItemClick(item: Item) {
                //productAdapter.removeItem(item)
            }
        })

        payerAdapter = PayerRecyclerAdapter(layoutInflater, object : OnItemClickListener {
            override fun onItemClick(item: Item) {
                //payerAdapter.removeItem(item)
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

    private fun initSwipeToDelete() {
        val productTouchHelper = ItemTouchHelper(SwipeListener { viewHolder, _ ->
            val pos = viewHolder?.adapterPosition
            factory.removedProduct(productAdapter.getItemNum(pos))
            productAdapter.removeItem(pos)
        })

        val payerTouchHelper = ItemTouchHelper(SwipeListener { viewHolder, _ ->
            val pos = viewHolder?.adapterPosition
            factory.removedPayer(payerAdapter.getItemNum(pos))
            payerAdapter.removeItem(pos)
        })

        productTouchHelper.attachToRecyclerView(list_products)
        payerTouchHelper.attachToRecyclerView(list_payers)
    }

    private fun initButtons() {
        add_product_button.setOnClickListener { productAdapter.addItem(factory.nextProduct()) }
        add_payer_button.setOnClickListener { payerAdapter.addItem(factory.nextPayer()) }
    }
}

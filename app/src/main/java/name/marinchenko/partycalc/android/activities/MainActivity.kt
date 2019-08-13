package name.marinchenko.partycalc.android.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.ItemKitFactory
import name.marinchenko.partycalc.android.util.adapter.PayerRecyclerAdapter
import name.marinchenko.partycalc.android.util.adapter.ProductRecyclerAdapter
import name.marinchenko.partycalc.android.util.adapter.ResultRecyclerAdapter
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.listener.SwipeListener
import name.marinchenko.partycalc.core.item.Item
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product
import name.marinchenko.partycalc.core.item.Result


class MainActivity : AppCompatActivity() {

    private val factory = ItemKitFactory(this)
    private lateinit var productsAdapter: ProductRecyclerAdapter
    private lateinit var payersAdapter: PayerRecyclerAdapter
    private lateinit var resultsAdapter: ResultRecyclerAdapter

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
        resultsAdapter.addResult(Result(
                Payer(0,"", ""),
                Payer(0,"", ""),
                0
        ))
    }

    private fun initAdapters() {
        productsAdapter = ProductRecyclerAdapter(layoutInflater, object : OnItemClickListener {
            override fun onItemClick(item: Item) {
                //productsAdapter.removeItem(item)
            }
        })

        payersAdapter = PayerRecyclerAdapter(layoutInflater, object : OnItemClickListener {
            override fun onItemClick(item: Item) {
                //payersAdapter.removeItem(item)
            }
        })

        resultsAdapter = ResultRecyclerAdapter(layoutInflater)

        list_products.adapter = productsAdapter
        list_payers.adapter = payersAdapter
        list_results.adapter = resultsAdapter
    }

    private fun initLayoutManagers() {
        list_products.layoutManager = LinearLayoutManager(this)
        list_payers.layoutManager = LinearLayoutManager(this)
        list_results.layoutManager = LinearLayoutManager(this)
    }

    private fun initSwipeToDelete() {
        val productTouchHelper = ItemTouchHelper(SwipeListener { viewHolder, _ ->
            productsAdapter.removeItem(viewHolder?.adapterPosition)
        })
        val payerTouchHelper = ItemTouchHelper(SwipeListener { viewHolder, _ ->
            payersAdapter.removeItem(viewHolder?.adapterPosition)
        })

        productTouchHelper.attachToRecyclerView(list_products)
        payerTouchHelper.attachToRecyclerView(list_payers)
    }

    private fun initButtons() {
        add_product_button.setOnClickListener {
            val kit = factory.nextProductKit()
            productsAdapter.addItem(Product(
                    kit.id,
                    kit.titleHint,
                    kit.sumHint,
                    kit.color
            ))
        }

        add_payer_button.setOnClickListener {
            val kit = factory.nextPayerKit()
            payersAdapter.addItem(Payer(
                    kit.id,
                    kit.titleHint,
                    kit.sumHint
            ))
        }
    }
}

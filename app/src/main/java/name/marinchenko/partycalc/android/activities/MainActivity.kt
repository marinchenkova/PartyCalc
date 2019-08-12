package name.marinchenko.partycalc.android.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.adapter.PayerRecyclerAdapter
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.adapter.ProductRecyclerAdapter
import name.marinchenko.partycalc.core.item.Item
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product


class MainActivity : AppCompatActivity() {

    private var productsAdapter: ProductRecyclerAdapter? = null
    private var payersAdapter: PayerRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLists()
    }

    private fun initLists() {
        initAdapters()
        initButtons()
    }

    private fun initAdapters() {
        productsAdapter = ProductRecyclerAdapter(layoutInflater, object : OnItemClickListener {
            override fun onItemClick(item: Item) {
                productsAdapter?.removeItem(item)
            }
        })

        payersAdapter = PayerRecyclerAdapter(layoutInflater, object : OnItemClickListener {
            override fun onItemClick(item: Item) {
                payersAdapter?.removeItem(item)
            }
        })

        list_products.layoutManager = LinearLayoutManager(this)
        list_payers.layoutManager = LinearLayoutManager(this)

        list_products.adapter = productsAdapter
        list_payers.adapter = payersAdapter
    }

    private fun initButtons() {
        add_product_button.setOnClickListener {
            productsAdapter?.addItem(Product(0))
        }

        add_payer_button.setOnClickListener {
            payersAdapter?.addItem(Payer(0))
        }
    }
}

package name.marinchenko.partycalc.android.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.adapter.ProductListAdapter
import name.marinchenko.partycalc.core.id.ProductId
import name.marinchenko.partycalc.core.item.Product


class MainActivity : AppCompatActivity() {

    private var productListAdapter: ProductListAdapter? = null

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
        productListAdapter = ProductListAdapter(layoutInflater, R.layout.item)
        list_products.adapter = productListAdapter
        list_products.setOnItemLongClickListener { _, _, position, _ ->
            productListAdapter?.removeItem(position)
            true
        }

    }

    private fun initButtons() {
        add_product_button.setOnClickListener {
            productListAdapter?.addItem(Product(ProductId(0)))
        }
    }
}

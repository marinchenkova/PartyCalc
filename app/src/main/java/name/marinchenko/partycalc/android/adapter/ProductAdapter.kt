package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.base.DataChangeObserverAdapter
import name.marinchenko.partycalc.android.util.item.ItemFactory
import name.marinchenko.partycalc.android.util.item.ProductFactory
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.android.viewHolder.ProductViewHolder
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater

class ProductAdapter(
        ctx: Context,
        private val clickListener: SimpleEventListener<Product>? = null,
        listListener: SimpleEventListener<List<Product>>? = null
): DataChangeObserverAdapter<ProductViewHolder, Product>(ctx, listListener) {


    private val editTextOnFocusListener = object : SimpleEventListener<Pair<Product, Int>> {
        override fun onEvent(item: Pair<Product, Int>) {
            if (!onBind) {
                editItem(item.first, item.second, false)
                observer.onChanged()
            }
        }
    }

    override val factory: ItemFactory<Product>
        get() = ProductFactory(ctx)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
                ctx,
                clickListener,
                editTextOnFocusListener,
                ctx.layoutInflater.inflate(
                        R.layout.product_item,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        onBindStart()
        holder.bind(list[position], position)
        onBindFinish()
    }
}

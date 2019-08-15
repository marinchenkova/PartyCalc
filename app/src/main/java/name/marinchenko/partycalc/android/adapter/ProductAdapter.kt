package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.android.util.itemFactory.ProductFactory
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.android.util.observer.ProductDataObserver
import name.marinchenko.partycalc.android.util.viewHolder.ProductViewHolder
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater

class ProductAdapter(
        ctx: Context,
        private val clickListener: SimpleEventListener<Product>? = null,
        private val listListener: SimpleEventListener<List<Product>>? = null
): AbstractItemAdapter<ProductViewHolder, Product>(ctx) {

    init {
        this.registerAdapterDataObserver(ProductDataObserver {
            listListener?.onEvent(list)
        })
    }

    override val factory: ItemFactory<Product>
        get() = ProductFactory(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ctx, clickListener, ctx.layoutInflater.inflate(
                R.layout.product_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}

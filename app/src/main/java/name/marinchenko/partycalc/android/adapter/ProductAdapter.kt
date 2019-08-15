package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.android.util.itemFactory.ProductFactory
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.ProductViewHolder
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater

class ProductAdapter(
        ctx: Context,
        private val listener: OnItemClickListener<Product>? = null
):
        AbstractItemAdapter<ProductViewHolder, Product>(ctx) {

    override val factory: ItemFactory<Product>
        get() = ProductFactory(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ctx, listener, ctx.layoutInflater.inflate(
                R.layout.product_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}

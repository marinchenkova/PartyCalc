package name.marinchenko.partycalc.android.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.android.util.itemFactory.PayerFactory
import name.marinchenko.partycalc.android.util.itemFactory.ProductFactory
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.ProductViewHolder
import org.jetbrains.anko.layoutInflater

class ProductRecyclerAdapter(
        ctx: Context,
        clickListener: OnItemClickListener
) : ItemRecyclerAdapter<ProductViewHolder>(
        ctx,
        R.layout.product_item,
        clickListener
) {

    override val factory: ItemFactory
        get() = ProductFactory(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ctx.layoutInflater.inflate(
                itemLayout,
                parent,
                false
        ))
    }

}

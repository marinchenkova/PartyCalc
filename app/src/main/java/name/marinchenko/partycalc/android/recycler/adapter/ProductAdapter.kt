package name.marinchenko.partycalc.android.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.recycler.adapter.base.TouchAdapter
import name.marinchenko.partycalc.android.recycler.factory.ProductFactory
import name.marinchenko.partycalc.android.recycler.viewHolder.ProductViewHolder
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater

class ProductAdapter(ctx: Context): TouchAdapter<ProductViewHolder, Product>(ctx) {

    private val factory = ProductFactory(ctx)

    fun newItem() {
        val item = factory.nextItem(
                list.map { it.num }.toHashSet(),
                list.map { it.id }.toHashSet()
        )
        addItem(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val holder = ProductViewHolder(ctx, ctx.layoutInflater.inflate(
                R.layout.product_item,
                parent,
                false
        ))
        return holder
                .onClickAction { item, position -> onItemClick?.invoke(item, position) }
                .onEditTextAction { item, position ->
                    if (!onBind) editItem(item, position, false)
                }
                as ProductViewHolder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        onBindStart()
        holder.bind(list[position], position)
        onBindFinish()
    }
}
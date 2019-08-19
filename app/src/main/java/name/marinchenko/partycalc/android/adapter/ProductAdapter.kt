package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.base.DataObserverAdapter
import name.marinchenko.partycalc.android.adapter.base.NewItemAdapter
import name.marinchenko.partycalc.android.util.item.ItemFactory
import name.marinchenko.partycalc.android.util.item.ProductFactory
import name.marinchenko.partycalc.android.viewHolder.ProductViewHolder
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater

class ProductAdapter(ctx: Context): NewItemAdapter<ProductViewHolder, Product>(ctx) {

    override val factory: ItemFactory<Product>
        get() = ProductFactory(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val holder = ProductViewHolder(ctx, ctx.layoutInflater.inflate(
                R.layout.product_item,
                parent,
                false
        ))
        return holder
                .onClickAction { item, position -> onItemClick?.invoke(item, position) }
                .onDragAction { onItemDrag?.invoke(it) }
                .onEditTextAction { item, position ->
                    if (!onBind) {
                        editItem(item, position, false)
                        observer.onChanged()
                    }
                }
                as ProductViewHolder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        onBindStart()
        holder.bind(list[position], position)
        onBindFinish()
    }
}

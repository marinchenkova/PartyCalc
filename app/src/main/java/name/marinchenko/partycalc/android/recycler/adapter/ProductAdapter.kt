package name.marinchenko.partycalc.android.recycler.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.recycler.adapter.base.ItemAdapter
import name.marinchenko.partycalc.android.recycler.factory.ProductFactory
import name.marinchenko.partycalc.android.recycler.viewHolder.ProductViewHolder
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.uiThread

class ProductAdapter(ctx: Context): ItemAdapter<ProductViewHolder, Product>(ctx) {

    private val factory = ProductFactory(ctx)


    fun newItem() {
        ctx.doAsync {
            val item = factory.nextItem(list)
            uiThread {
                addItem(item)
            }
        }

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

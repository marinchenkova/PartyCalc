package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.base.UndoRemoveItemAdapter
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.android.util.itemFactory.ProductFactory
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.android.util.observer.ProductDataObserver
import name.marinchenko.partycalc.android.util.viewHolder.ProductViewHolder
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.toast

class ProductAdapter(
        ctx: Context,
        private val clickListener: SimpleEventListener<Product>? = null,
        private val listListener: SimpleEventListener<List<Product>>? = null
): UndoRemoveItemAdapter<ProductViewHolder, Product>(ctx) {

    private val observer = ProductDataObserver { listListener?.onEvent(list) }

    private val editTextListener = object : SimpleEventListener<Pair<Product, Int>> {
        override fun onEvent(item: Pair<Product, Int>) {
            editItem(item.first, item.second)
            observer.onChanged()
        }
    }


    init {
        registerAdapterDataObserver(observer)
    }

    override val factory: ItemFactory<Product>
        get() = ProductFactory(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ctx, clickListener, editTextListener, ctx.layoutInflater.inflate(
                R.layout.product_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}

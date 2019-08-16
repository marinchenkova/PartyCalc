package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.base.UndoRemoveItemAdapter
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.android.util.itemFactory.ProductFactory
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.android.util.observer.ProductDataObserver
import name.marinchenko.partycalc.android.viewHolder.ProductViewHolder
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.toast

class ProductAdapter(
        ctx: Context,
        private val clickListener: SimpleEventListener<Product>? = null,
        private val listListener: SimpleEventListener<List<Product>>? = null
): UndoRemoveItemAdapter<ProductViewHolder, Product>(ctx) {

    private val observer = ProductDataObserver { listListener?.onEvent(list) }

    private val editTextOnFocusListener = object : SimpleEventListener<Pair<Product, Int>> {
        override fun onEvent(item: Pair<Product, Int>) {
            if (!onBind) {
                editItem(item.first, item.second)
                observer.onChanged()
            }
        }
    }

    override val factory: ItemFactory<Product>
        get() = ProductFactory(ctx)


    init {
        registerAdapterDataObserver(observer)
    }

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

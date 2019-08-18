package name.marinchenko.partycalc.android.viewHolder

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.product_item.view.*
import name.marinchenko.partycalc.android.util.afterInput
import name.marinchenko.partycalc.android.util.listener.ItemEventListener
import name.marinchenko.partycalc.android.viewHolder.base.AbstractItemViewHolder
import name.marinchenko.partycalc.core.item.Product

class ProductViewHolder(
        ctx: Context,
        private val listener: ItemEventListener<Product>?,
        private val editTextOnFocusListener: ItemEventListener<Pair<Product, Int>>?,
        view: View?
): AbstractItemViewHolder<Product>(ctx, view) {

    override fun bind(item: Product, position: Int) {
        itemView.setOnClickListener { listener?.onEvent(item) }

        itemView?.item_title?.setText(item.title)
        itemView?.item_title?.hint = item.hintTitle
        itemView?.item_title?.afterInput { text -> editTextOnFocusListener?.onEvent(Pair(
                item.also { it.title = text },
                position
        ))}

        itemView?.item_sum?.setText(item.sumString)
        itemView?.item_sum?.hint = item.hintSum
        itemView?.item_sum?.afterInput { text -> editTextOnFocusListener?.onEvent(Pair(
                item.also { it.sumString = text },
                position
        ))}

        itemView?.card_view?.setCardBackgroundColor(item.color)
    }

}
package name.marinchenko.partycalc.android.util.viewHolder

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.product_item.view.*
import name.marinchenko.partycalc.android.util.afterInput
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.core.item.Product

class ProductViewHolder(
        ctx: Context,
        private val listener: SimpleEventListener<Product>?,
        private val editTextListener: SimpleEventListener<Pair<Product, Int>>?,
        view: View?
): AbstractItemViewHolder<Product>(ctx, view) {

    override fun bind(item: Product, position: Int) {
        itemView.setOnClickListener { listener?.onEvent(item) }

        itemView?.item_title?.setText(item.title)
        itemView?.item_title?.hint = item.hintTitle
        itemView?.item_title?.afterInput { text -> editTextListener?.onEvent(Pair(
                item.also { it.title = text },
                position
        ))}

        itemView?.item_sum?.setText(item.sumString)
        itemView?.item_sum?.hint = item.hintSum
        itemView?.item_sum?.afterInput { text -> editTextListener?.onEvent(Pair(
                item.also { it.sumString = text },
                position
        ))}

        itemView?.card_view?.setCardBackgroundColor(item.color)
    }

}
package name.marinchenko.partycalc.android.util.viewHolder

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.product_item.view.*
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.core.item.Product

class ProductViewHolder(
        ctx: Context,
        private val listener: SimpleEventListener<Product>?,
        view: View?
): AbstractItemViewHolder<Product>(ctx, view) {

    override fun bind(item: Product, position: Int) {
        itemView.setOnClickListener { listener?.onEvent(item) }

        itemView?.item_title?.setText(item.title)
        itemView?.item_title?.hint = item.hintTitle

        itemView?.item_sum?.setText(item.sumString)
        itemView?.item_sum?.hint = item.hintSum

        itemView?.card_view?.setCardBackgroundColor(item.color)
    }

}
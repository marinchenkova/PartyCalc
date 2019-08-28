package name.marinchenko.partycalc.android.recycler.viewHolder

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.product_item.view.*
import name.marinchenko.partycalc.android.util.afterInput
import name.marinchenko.partycalc.android.recycler.viewHolder.base.AbstractItemViewHolder
import name.marinchenko.partycalc.core.item.Product

class ProductViewHolder(ctx: Context, view: View): AbstractItemViewHolder<Product>(ctx, view) {

    override fun bind(item: Product, position: Int) {
        itemView.setOnClickListener { onClick?.invoke(item, position) }
        itemView.handle?.setOnTouchListener { _, event ->
            if (event?.action == MotionEvent.ACTION_DOWN) onDrag?.invoke(this)
            true
        }

        itemView.item_title?.setText(item.title)
        itemView.item_title?.hint = item.hintTitle
        itemView.item_title?.afterInput { text -> onEditText?.invoke(
                item.also { it.title = text },
                position
        )}

        itemView.item_sum?.setText(item.sumString)
        itemView.item_sum?.hint = item.hintSum
        itemView.item_sum?.afterInput { text -> onEditText?.invoke(
                item.also { it.sumString = text },
                position
        )}

        itemView.card_view?.setCardBackgroundColor(item.color)
    }

}
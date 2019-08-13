package name.marinchenko.partycalc.android.util.viewHolder

import android.support.v7.widget.CardView
import android.view.View
import android.widget.EditText
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.core.item.Item
import name.marinchenko.partycalc.core.item.Product

class ProductViewHolder(view: View?): ItemViewHolder(view) {

    override fun bind(item: Item, listener: OnItemClickListener) {
        item as Product
        itemView.setOnClickListener { listener.onItemClick(item) }

        val titleView = itemView?.findViewById<EditText>(R.id.item_title)
        titleView?.setText(item.title)
        titleView?.hint = item.hintTitle

        val sumView = itemView.findViewById<EditText>(R.id.item_sum)
        sumView?.setText(item.sumString)
        sumView?.hint = item.hintSum

        itemView.findViewById<CardView>(R.id.card_view).setCardBackgroundColor(item.color)
    }

}
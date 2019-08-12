package name.marinchenko.partycalc.android.util.viewHolder

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
        itemView?.findViewById<EditText>(R.id.item_title)?.setText(item.title)
        itemView.findViewById<EditText>(R.id.item_sum)?.setText(item.sumString())
    }

}
package name.marinchenko.partycalc.android.util.viewHolder

import android.view.View
import android.widget.EditText
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.core.item.Item
import name.marinchenko.partycalc.core.item.Payer

class PayerViewHolder(view: View?): ItemViewHolder(view) {

    override fun bind(item: Item, clickListener: OnItemClickListener?) {
        item as Payer
        itemView.setOnClickListener { clickListener?.onItemClick(item) }

        val titleView = itemView?.findViewById<EditText>(R.id.item_title)
        titleView?.setText(item.title)
        titleView?.hint = item.hintTitle

        val sumView = itemView.findViewById<EditText>(R.id.item_sum)
        sumView?.setText(item.sumString)
        sumView?.hint = item.hintSum
    }

}
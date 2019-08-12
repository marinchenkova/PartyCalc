package name.marinchenko.partycalc.android.util.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.core.item.Item

abstract class ItemViewHolder(view: View?): RecyclerView.ViewHolder(view) {

    abstract fun bind(item: Item, listener: OnItemClickListener)

}
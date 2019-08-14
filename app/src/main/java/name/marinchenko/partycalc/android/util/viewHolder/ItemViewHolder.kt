package name.marinchenko.partycalc.android.util.viewHolder

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.makeVibration
import name.marinchenko.partycalc.core.item.Item
import org.jetbrains.anko.backgroundColor

abstract class ItemViewHolder(view: View?): RecyclerView.ViewHolder(view) {

    private var background: Drawable? = null

    abstract fun bind(item: Item, clickListener: OnItemClickListener?)

    fun onItemSelected() {
        background = itemView.background
        itemView.backgroundColor = Color.LTGRAY
        itemView.context.makeVibration(30, 5)
    }

    fun onItemClear() {
        itemView.background = background
    }

}
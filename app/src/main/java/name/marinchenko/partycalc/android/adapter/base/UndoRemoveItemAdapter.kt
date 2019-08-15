package name.marinchenko.partycalc.android.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import name.marinchenko.partycalc.core.item.IItem

abstract class UndoRemoveItemAdapter<VH: RecyclerView.ViewHolder, I: IItem>(ctx: Context):
        BaseItemAdapter<VH, I>(ctx), UndoRemoveAdapter {

    private lateinit var lastRemovedItem: I
    private var lastRemovedItemPos: Int? = null

    override fun removeItem(position: Int?) {
        position ?: return
        lastRemovedItemPos = position
        lastRemovedItem = list[position]
        list.removeAt(position)
        notifyDataSetChanged()
    }

    override fun undoRemoveItem() {
        val pos = lastRemovedItemPos ?: -1
        if (pos >= 0) {
            list.add(pos, lastRemovedItem)
            notifyItemInserted(pos)
        }
    }

}
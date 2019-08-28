package name.marinchenko.partycalc.android.recycler.adapter.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import name.marinchenko.partycalc.core.item.IdItem

abstract class UndoRemoveAdapter<VH: RecyclerView.ViewHolder, I: IdItem>(ctx: Context):
        BaseIdItemAdapter<VH, I>(ctx) {

    private lateinit var lastRemovedItem: I
    private var lastRemovedItemPos: Int? = null


    override fun removeItem(position: Int?) {
        position ?: return
        lastRemovedItemPos = position
        lastRemovedItem = list[position]
        list.removeAt(position)
        notifyDataSetChanged()
        callback?.onRemoveItem(lastRemovedItem, position)
    }

    fun undoRemoveItem() {
        val pos = lastRemovedItemPos ?: -1
        if (pos >= 0) {
            list.add(pos, lastRemovedItem)
            notifyItemInserted(pos)
            callback?.onAddItem(lastRemovedItem, pos, true)
        }
    }

}
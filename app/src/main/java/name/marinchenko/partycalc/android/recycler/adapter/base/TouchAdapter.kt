package name.marinchenko.partycalc.android.recycler.adapter.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import name.marinchenko.partycalc.core.item.IdItem

abstract class TouchAdapter<VH: RecyclerView.ViewHolder, I: IdItem>(ctx: Context):
        UndoRemoveAdapter<VH, I>(ctx) {

    protected var onItemClick: ((item: I, position: Int) -> Unit)? = null
    protected var onItemDrag: ((holder: RecyclerView.ViewHolder) -> Unit)? = null


    fun onItemClick(action: (item: I, position: Int) -> Unit): TouchAdapter<*,*> {
        onItemClick = action
        return this
    }

    fun onItemDrag(action: (holder: RecyclerView.ViewHolder) -> Unit): TouchAdapter<*,*> {
        onItemDrag = action
        return this
    }

}
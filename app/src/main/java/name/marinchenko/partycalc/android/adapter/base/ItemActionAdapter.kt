package name.marinchenko.partycalc.android.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import name.marinchenko.partycalc.core.item.IdItem

abstract class ItemActionAdapter<VH: RecyclerView.ViewHolder, I: IdItem>(ctx: Context):
        UndoRemoveItemAdapter<VH, I>(ctx) {

    protected var onItemClick: ((item: I, position: Int) -> Unit)? = null
    protected var onItemDrag: ((holder: RecyclerView.ViewHolder) -> Unit)? = null
    protected var onListChanged: ((list: List<I>) -> Unit)? = null


    open fun onItemClick(action: (item: I, position: Int) -> Unit): ItemActionAdapter<*, *>  {
        onItemClick = action
        return this
    }

    fun onItemDrag(action: (holder: RecyclerView.ViewHolder) -> Unit): ItemActionAdapter<*, *>  {
        onItemDrag = action
        return this
    }

    fun onListChanged(action: (list: List<I>) -> Unit): ItemActionAdapter<*, *>  {
        onListChanged = action
        return this
    }

}
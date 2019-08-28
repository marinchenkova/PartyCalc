package name.marinchenko.partycalc.android.recycler.adapter.base

import name.marinchenko.partycalc.core.item.IdItem

interface IdItemAdapter<I: IdItem> {

    fun addItem(item: I)

    fun removeItem(position: Int?)

    fun moveItem(from: Int?, to: Int?): Boolean

    fun editItem(item: I, position: Int, notify: Boolean = true)

    fun load(new: List<I>)


    interface Callback<I> {

        fun onAddItem(item: I, position: Int, undoRemove: Boolean = false)

        fun onRemoveItem(item: I, position: Int)

        fun onMoveItems(from: Int, to: Int)

        fun onEditItem(item: I, position: Int)

        fun onUpdateList(new: List<I>)

    }

}
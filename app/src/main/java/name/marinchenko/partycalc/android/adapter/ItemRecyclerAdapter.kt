package name.marinchenko.partycalc.android.adapter

import name.marinchenko.partycalc.core.item.IItem

interface ItemRecyclerAdapter<I: IItem>: SimpleItemAdapter<I> {

    override fun addItem(item: I)

    override fun removeItem(position: Int?)

    fun newItem()

    fun moveItem(from: Int?, to: Int?): Boolean

}
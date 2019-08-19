package name.marinchenko.partycalc.android.adapter.base

import name.marinchenko.partycalc.core.item.IItem

interface ItemAdapter<I: IItem>: SimpleAdapter<I> {

    override fun addItem(item: I)

    override fun removeItem(position: Int?)

    fun newItem(sum: String = "")

    fun moveItem(from: Int?, to: Int?): Boolean

    fun editItem(item: I, position: Int, notify: Boolean = true)

    fun update(new: List<I>)

}
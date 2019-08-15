package name.marinchenko.partycalc.android.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.core.item.IItem
import name.marinchenko.partycalc.core.item.Product
import java.util.*

abstract class BaseItemAdapter<VH: RecyclerView.ViewHolder, I: IItem>(protected val ctx: Context):
        RecyclerView.Adapter<VH>(), ItemAdapter<I> {

    protected val list = mutableListOf<I>()
    protected abstract val factory: ItemFactory<I>

    override fun newItem() {
        addItem(factory.nextItem(
                list.map { it.num }.toHashSet(),
                list.map { it.id }.toHashSet()
        ))
    }

    override fun addItem(item: I) {
        list.add(item)
        notifyDataSetChanged()
    }

    override fun editItem(item: I, position: Int) {
        if (list.contains(item) && list.indexOf(item) == position) {
            list[position] = item
            notifyItemChanged(position)
        }
    }

    override fun moveItem(from: Int?, to: Int?): Boolean {
        if (from == null || to == null) return false

        if (from < to) {
            for (i in from until to) Collections.swap(list, i, i + 1)
        }
        else {
            for (i in from downTo to + 1) {
                Collections.swap(list, i, i - 1)
            }
        }

        notifyItemMoved(from, to)
        return true
    }

    override fun removeItem(position: Int?) {
        position ?: return
        list.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int) = list[position].id

    override fun getItemCount() = list.size

}
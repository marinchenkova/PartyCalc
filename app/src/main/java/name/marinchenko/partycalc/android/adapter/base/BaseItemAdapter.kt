package name.marinchenko.partycalc.android.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import name.marinchenko.partycalc.android.util.item.ItemFactory
import name.marinchenko.partycalc.core.item.IItem
import java.util.*

abstract class BaseItemAdapter<VH: RecyclerView.ViewHolder, I: IItem>(protected val ctx: Context):
        RecyclerView.Adapter<VH>(), ItemAdapter<I>, BinderAdapter {

    protected var onBind = false
        private set

    protected val list = mutableListOf<I>()
    protected abstract val factory: ItemFactory<I>


    fun getItems() = list

    override fun update(new: List<I>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    override fun newItem(sum: String) {
        addItem(factory.nextItem(
                list.map { it.num }.toHashSet(),
                list.map { it.id }.toHashSet(),
                sum
        ))
    }

    override fun addItem(item: I) {
        list.add(item)
        notifyDataSetChanged()
    }

    override fun editItem(item: I, position: Int, notify: Boolean) {
        if (list.contains(item) && list.indexOf(item) == position) {
            list[position] = item
            if (notify) notifyItemChanged(position)
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

    override fun onBindStart() {
        onBind = true
    }

    override fun onBindFinish() {
        onBind = false
    }
}
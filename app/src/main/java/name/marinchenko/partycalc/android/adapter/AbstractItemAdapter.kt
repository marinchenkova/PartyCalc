package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.core.item.IItem
import java.util.*

abstract class AbstractItemAdapter<VH: RecyclerView.ViewHolder, I: IItem>(
        protected val ctx: Context
): RecyclerView.Adapter<VH>(), ItemRecyclerAdapter<I>, UndoRemoveItemAdapter {

    protected val list = mutableListOf<I>()
    protected abstract val factory: ItemFactory<I>
    private lateinit var lastRemovedItem: I
    private var lastRemovedItemPos: Int? = null

    override fun newItem() {
        addItem(factory.nextItem(list.map { it.num }.toSet()))
    }

    override fun addItem(item: I) {
        list.add(item)
        notifyDataSetChanged()
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

    override fun getItemId(position: Int) = list[position].id

    override fun getItemCount() = list.size

}
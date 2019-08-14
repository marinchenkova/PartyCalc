package name.marinchenko.partycalc.android.util.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.ItemViewHolder
import name.marinchenko.partycalc.core.item.Item
import java.util.*


abstract class ItemRecyclerAdapter<VH: ItemViewHolder>(
        protected val inflater: LayoutInflater,
        @LayoutRes protected val itemLayout: Int,
        private val clickListener: OnItemClickListener?
) : RecyclerView.Adapter<VH>() {

    private val list = mutableListOf<Item>()
    private lateinit var lastRemovedItem: Item
    private var lastRemovedItemPos: Int? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position], clickListener)
    }

    fun addItem(item: Item) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun moveItem(from: Int?, to: Int?): Boolean {
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

    private fun removeItem(item: Item?) {
        item ?: return
        lastRemovedItem = item
        lastRemovedItemPos = list.indexOf(item)
        list.remove(item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int?) {
        position ?: return
        removeItem(list[position])
    }

    fun undoRemoveItem() {
        val pos = lastRemovedItemPos ?: -1
        if (pos >= 0) {
            list.add(pos, lastRemovedItem)
            notifyItemInserted(pos)
        }
    }

    override fun getItemId(position: Int) = list[position].id

    fun getItemNum(position: Int?) =
            if (position == null) 0
            else list[position].num

    override fun getItemCount() = list.size
}
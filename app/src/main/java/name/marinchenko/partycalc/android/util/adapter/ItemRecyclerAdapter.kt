package name.marinchenko.partycalc.android.util.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.ItemViewHolder
import name.marinchenko.partycalc.core.item.Item


abstract class ItemRecyclerAdapter<VH: ItemViewHolder>(
        protected val inflater: LayoutInflater,
        @LayoutRes protected val itemLayout: Int,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<VH>() {

    private val list = mutableListOf<Item>()

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position], listener)
    }

    fun addItem(item: Item) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun removeItem(item: Item?) {
        list.remove(item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int?) {
        position ?: return
        list.removeAt(position)
        notifyDataSetChanged()
    }

    fun getItemNum(position: Int?) =
            if (position == null) 0
            else list[position].num

    override fun getItemCount() = list.size
}
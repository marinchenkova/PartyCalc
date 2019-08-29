package name.marinchenko.partycalc.android.recycler.adapter.base

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import name.marinchenko.partycalc.android.recycler.BindListener
import name.marinchenko.partycalc.android.util.swapItems
import name.marinchenko.partycalc.core.item.IdItem
import org.jetbrains.anko.doAsync
import java.util.*

abstract class BaseIdItemAdapter<VH: RecyclerView.ViewHolder, I: IdItem>(protected val ctx: Context):
        RecyclerView.Adapter<VH>(), IdItemAdapter<I>, BindListener {

    var callback: IdItemAdapter.Callback<I>? = null
    protected val list = mutableListOf<I>()
    protected var onBind = false
        private set


    fun getItems() = list

    override fun load(new: List<I>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    override fun addItem(item: I) {
        list.add(item)
        notifyItemInserted(list.size)
        callback?.onAddItem(item, list.indexOf(item))
    }

    override fun editItem(item: I, position: Int, notify: Boolean) {
        if (list.contains(item)) {
            list[list.indexOf(item)] = item
            if (notify) notifyItemChanged(position)
            callback?.onEditItem(item, position)
        }
    }

    override fun removeItem(position: Int?) {
        position ?: return
        val toRemove = list[position]
        list.removeAt(position)
        notifyDataSetChanged()
        callback?.onRemoveItem(toRemove, position)
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
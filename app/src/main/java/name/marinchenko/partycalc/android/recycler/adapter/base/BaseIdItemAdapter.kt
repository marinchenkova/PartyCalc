package name.marinchenko.partycalc.android.recycler.adapter.base

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import name.marinchenko.partycalc.android.recycler.listener.BindListener
import name.marinchenko.partycalc.core.item.IdItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

abstract class BaseIdItemAdapter<VH: RecyclerView.ViewHolder, I: IdItem>(protected val ctx: Context):
        RecyclerView.Adapter<VH>(), IdItemAdapter<I>, BindListener {

    private var onLoad: (() -> Unit)? = null
    var callback: IdItemAdapter.Callback<I>? = null
    protected val list = mutableListOf<I>()
    protected var onBind = false
        private set


    fun getItems() = list

    fun onLoad(action: () -> Unit): BaseIdItemAdapter<*,*> {
        onLoad = action
        return this
    }

    override fun load(new: List<I>) {
        ctx.doAsync {
            list.clear()
            list.addAll(new)
            uiThread {
                notifyDataSetChanged()
                onLoad?.invoke()
            }
        }
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
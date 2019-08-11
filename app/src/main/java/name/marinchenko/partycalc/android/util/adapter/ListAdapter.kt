package name.marinchenko.partycalc.android.util.adapter

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import name.marinchenko.partycalc.core.id.Id
import name.marinchenko.partycalc.core.item.Item

abstract class ListAdapter(
        private val inflater: LayoutInflater,
        @LayoutRes private val itemLayout: Int
) : BaseAdapter() {

    private val list = mutableListOf<Item<*>>()

    abstract fun fillView(item: Item<*>?, view: View?)

    protected fun getItemById(itemId: Id<*>) = list.find { it.id == itemId }

    fun addItem(item: Item<*>) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun removeItem(item: Item<*>?) {
        list.remove(item)
        notifyDataSetChanged()
    }

    fun removeItem(itemId: Id<*>) { removeItem(getItemById(itemId)) }
    fun removeItem(position: Int) { removeItem(getItem(position)) }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        if (view == null) view = inflater.inflate(itemLayout, parent, false)
        fillView(getItem(position), view)
        return view
    }

    override fun getItem(position: Int) = list[position]
    override fun getItemId(position: Int) = list[position].id.toLong()
    override fun getCount() = list.size
}
package name.marinchenko.partycalc.android.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import name.marinchenko.partycalc.android.util.item.ItemFactory
import name.marinchenko.partycalc.core.item.NumItem

abstract class NewItemAdapter<VH: RecyclerView.ViewHolder, I: NumItem>(ctx: Context):
        DataObserverAdapter<VH, I>(ctx) {

    protected abstract val factory: ItemFactory<I>

    open fun newItem() {
        addItem(factory.nextItem(
                list.map { it.num }.toHashSet(),
                list.map { it.id }.toHashSet()
        ))
    }

}
package name.marinchenko.partycalc.android.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import name.marinchenko.partycalc.android.util.observer.DataObserver
import name.marinchenko.partycalc.core.item.IdItem

abstract class DataObserverAdapter<VH: RecyclerView.ViewHolder, I: IdItem>(ctx: Context):
        ItemActionAdapter<VH, I>(ctx) {

    protected val observer = DataObserver { onListChanged?.invoke(list) }

    init {
        this.registerAdapterDataObserver(observer)
    }

}
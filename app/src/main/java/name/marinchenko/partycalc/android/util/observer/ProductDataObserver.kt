package name.marinchenko.partycalc.android.util.observer

import android.support.v7.widget.RecyclerView

class ProductDataObserver(private val action: () -> Unit): RecyclerView.AdapterDataObserver() {

    override fun onChanged() {
        action()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        action()
    }
}
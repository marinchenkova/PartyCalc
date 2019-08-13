package name.marinchenko.partycalc.android.util.listener

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import name.marinchenko.partycalc.android.util.adapter.ItemRecyclerAdapter

class SwipeToDeleteListener(
        private val adapter: ItemRecyclerAdapter<*>
): ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    override fun onMove(view: RecyclerView?,
                        holder: RecyclerView.ViewHolder?,
                        target: RecyclerView.ViewHolder?) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        adapter.removeItem(viewHolder?.adapterPosition)
    }

}
package name.marinchenko.partycalc.android.util.listener

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class ItemTouchListener(
        private val onMoveAction: (view: RecyclerView?,
                                   holder: RecyclerView.ViewHolder?,
                                   target: RecyclerView.ViewHolder?) -> Boolean,
        private val onSwipeAction: (holder: RecyclerView.ViewHolder?, direction: Int) -> Unit
): ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.START or ItemTouchHelper.END
) {

    override fun isLongPressDragEnabled() = true
    override fun isItemViewSwipeEnabled() = true

    override fun onMove(view: RecyclerView?,
                        holder: RecyclerView.ViewHolder?,
                        target: RecyclerView.ViewHolder?) =
            onMoveAction(view, holder, target)

    override fun onSwiped(holder: RecyclerView.ViewHolder?, direction: Int) =
            onSwipeAction(holder, direction)

}
package name.marinchenko.partycalc.android.util.listener

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import name.marinchenko.partycalc.android.util.viewHolder.ItemViewHolder

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

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is ItemViewHolder) {
                viewHolder.onItemSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is ItemViewHolder) {
            viewHolder.onItemClear()
        }
    }
}
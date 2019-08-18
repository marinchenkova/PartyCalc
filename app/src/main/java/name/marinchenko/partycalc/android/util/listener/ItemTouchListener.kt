package name.marinchenko.partycalc.android.util.listener

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import name.marinchenko.partycalc.android.viewHolder.base.SelectableItemViewHolder

class ItemTouchListener : ItemTouchHelper.SimpleCallback(
        UP or DOWN or START or END,
        START or END
) {

    private lateinit var moveAction: (
            view: RecyclerView?,
            holder: RecyclerView.ViewHolder?,
            target: RecyclerView.ViewHolder?
    ) -> Boolean

    private lateinit var swipeAction: (holder: RecyclerView.ViewHolder?, direction: Int) -> Unit


    fun onMoveAction(action: (
            view: RecyclerView?,
            holder: RecyclerView.ViewHolder?,
            target: RecyclerView.ViewHolder?
    ) -> Boolean): ItemTouchListener {
        moveAction = action
        return this
    }

    fun onSwipeAction(action: (
            holder: RecyclerView.ViewHolder?,
            direction: Int
    ) -> Unit): ItemTouchListener {
        swipeAction = action
        return this
    }

    override fun isLongPressDragEnabled() = false
    override fun isItemViewSwipeEnabled() = true

    override fun onMove(view: RecyclerView?,
                        holder: RecyclerView.ViewHolder?,
                        target: RecyclerView.ViewHolder?) =
            moveAction(view, holder, target)

    override fun onSwiped(holder: RecyclerView.ViewHolder?, direction: Int) =
            swipeAction(holder, direction)

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            if (viewHolder is SelectableItemViewHolder) {
                viewHolder.onItemSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is SelectableItemViewHolder) {
            viewHolder.onItemClear()
        }
    }
}
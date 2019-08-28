package name.marinchenko.partycalc.android.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*

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

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean =
            moveAction(recyclerView, viewHolder, target)

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
            swipeAction(viewHolder, direction)

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState == ACTION_STATE_DRAG) {
            if (viewHolder is SelectListener) {
                viewHolder.onSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is SelectListener) {
            viewHolder.onClear()
        }
    }
}
package name.marinchenko.partycalc.android.util.listener

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class SwipeListener(
        private val onSwipe: (holder: RecyclerView.ViewHolder?, dir: Int) -> Unit
): ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    override fun onMove(view: RecyclerView?,
                        holder: RecyclerView.ViewHolder?,
                        target: RecyclerView.ViewHolder?) = false

    override fun onSwiped(holder: RecyclerView.ViewHolder?, direction: Int) =
            onSwipe(holder, direction)

}
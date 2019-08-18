package name.marinchenko.partycalc.android.viewHolder.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.highlight
import name.marinchenko.partycalc.android.util.makeVibration
import name.marinchenko.partycalc.core.item.IItem
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.backgroundColor

abstract class AbstractItemViewHolder<I: IItem>(protected val ctx: Context, view: View?):
        RecyclerView.ViewHolder(view), SelectableItemViewHolder, BinderViewHolder<I> {

    protected var onClick: ((item: I, position: Int) -> Unit)? = null
    protected var onDrag: ((holder: RecyclerView.ViewHolder) -> Unit)? = null
    protected var onEditText: ((item: I, position: Int) -> Unit)? = null

    fun onClickAction(action: (item: I, position: Int) -> Unit): AbstractItemViewHolder<I> {
        onClick = action
        return this
    }

    fun onDragAction(action: (holder: RecyclerView.ViewHolder) -> Unit): AbstractItemViewHolder<I> {
        onDrag = action
        return this
    }

    fun onEditTextAction(action: (item: I, position: Int) -> Unit): AbstractItemViewHolder<I> {
        onEditText = action
        return this
    }

    override fun onItemSelected() {
        itemView?.highlight(true)
    }

    override fun onItemClear() {
        itemView?.highlight(false)
    }

}
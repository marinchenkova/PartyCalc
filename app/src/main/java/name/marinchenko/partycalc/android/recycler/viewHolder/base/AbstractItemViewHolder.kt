package name.marinchenko.partycalc.android.recycler.viewHolder.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import name.marinchenko.partycalc.android.recycler.listener.SelectListener
import name.marinchenko.partycalc.android.util.highlight
import name.marinchenko.partycalc.core.item.IdItem

abstract class AbstractItemViewHolder<I: IdItem>(protected val ctx: Context, view: View):
        RecyclerView.ViewHolder(view), SelectListener, BinderViewHolder<I> {

    protected var onClick: ((item: I, position: Int) -> Unit)? = null
    protected var onEditText: ((item: I, position: Int) -> Unit)? = null

    fun onClickAction(action: (item: I, position: Int) -> Unit): AbstractItemViewHolder<I> {
        onClick = action
        return this
    }

    fun onEditTextAction(action: (item: I, position: Int) -> Unit): AbstractItemViewHolder<I> {
        onEditText = action
        return this
    }

    override fun onSelected() {
        itemView.highlight(true)
    }

    override fun onClear() {
        itemView.highlight(false)
    }

}
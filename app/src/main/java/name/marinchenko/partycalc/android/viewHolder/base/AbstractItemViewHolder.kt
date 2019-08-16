package name.marinchenko.partycalc.android.viewHolder.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.makeVibration
import name.marinchenko.partycalc.core.item.IItem
import org.jetbrains.anko.backgroundColor

abstract class AbstractItemViewHolder<I: IItem>(protected val ctx: Context, view: View?):
        RecyclerView.ViewHolder(view), SelectableItemViewHolder, BinderViewHolder<I> {

    private var background: Drawable? = null

    override fun onItemSelected() {
        background = itemView.background
        itemView.backgroundColor = ctx.getColor(R.color.colorHighlight)
        itemView.context.makeVibration(30, 5)
    }

    override fun onItemClear() {
        itemView.background = background
    }

}
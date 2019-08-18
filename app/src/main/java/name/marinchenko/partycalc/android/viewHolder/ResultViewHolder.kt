package name.marinchenko.partycalc.android.viewHolder

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.result_item.view.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.viewHolder.base.BinderViewHolder
import name.marinchenko.partycalc.core.formatDouble
import name.marinchenko.partycalc.core.item.Result
import org.jetbrains.anko.backgroundColor

class ResultViewHolder(private val ctx: Context, view: View?):
        RecyclerView.ViewHolder(view), BinderViewHolder<Result> {

    private var background: Drawable? = null
    private var onDone: ((isDone: Boolean, position: Int) -> Unit)? = null

    override fun bind(item: Result, position: Int) {
        itemView?.result_who?.text = item.who.title
        itemView?.result_who?.hint = item.who.hintTitle

        itemView?.result_to_whom?.text = item.toWhom.title
        itemView?.result_to_whom?.hint= item.toWhom.hintTitle

        itemView?.result_amount?.text = formatDouble(item.sum)

        itemView.result_done?.isChecked = item.done
        itemView.result_done?.setOnClickListener {
            val checked = itemView.result_done?.isChecked == true

            if (checked) {
                background = itemView.background
                itemView.backgroundColor = ctx.getColor(R.color.colorGreen_done)
            }
            else itemView?.background = background

            onDone?.invoke(checked, position)
        }
    }

    fun onDoneAction(action: (isDone: Boolean, position: Int) -> Unit): ResultViewHolder {
        onDone = action
        return this
    }

}
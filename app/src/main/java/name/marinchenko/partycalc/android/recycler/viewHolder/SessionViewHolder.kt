package name.marinchenko.partycalc.android.recycler.viewHolder

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import kotlinx.android.synthetic.main.session_item.*
import kotlinx.android.synthetic.main.session_item.view.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.storage.session.Session
import name.marinchenko.partycalc.android.util.afterInput
import name.marinchenko.partycalc.android.util.spanDiff
import name.marinchenko.partycalc.android.recycler.viewHolder.base.AbstractItemViewHolder
import name.marinchenko.partycalc.android.util.setVisible
import name.marinchenko.partycalc.core.PartyCalc

class SessionViewHolder(ctx: Context, view: View): AbstractItemViewHolder<Session>(ctx, view) {

    override fun bind(item: Session, position: Int) {
        itemView.setOnClickListener { onClick?.invoke(item, position) }

        itemView.item_title?.setText(item.title)
        itemView.item_title?.hint = item.hintTitle
        itemView.item_title?.afterInput { text -> onEditText?.invoke(
                item.also { it.title = text },
                position
        )}

        val dateString = "${ctx.getString(R.string.added_on)} ${item.getDateString()}"
        itemView.date?.text = dateString

        val sumString = spanDiff(
                ctx.getString(R.string.summary_sum),
                PartyCalc.itemListSumString(item.products),
                getColor(ctx, R.color.colorPrimary)
        )
        itemView.summary_sum?.text = sumString

        val doneString = spanDiff(
                ctx.getString(R.string.summary_done),
                PartyCalc.resultsDone(item.results),
                getColor(ctx, R.color.colorPrimary)
        )
        itemView.summary_done?.text = doneString
    }

}
package name.marinchenko.partycalc.android.viewHolder

import android.content.Context
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.session_item.view.*
import name.marinchenko.partycalc.android.storage.Session
import name.marinchenko.partycalc.android.util.afterInput
import name.marinchenko.partycalc.android.viewHolder.base.AbstractItemViewHolder

class SessionViewHolder(ctx: Context, view: View?): AbstractItemViewHolder<Session>(ctx, view) {

    override fun bind(item: Session, position: Int) {
        itemView?.setOnClickListener { onClick?.invoke(item, position) }
        itemView?.handle?.setOnTouchListener { _, event ->
            if (event?.action == MotionEvent.ACTION_DOWN) onDrag?.invoke(this)
            true
        }

        itemView?.item_title?.setText(item.title)
        itemView?.item_title?.hint = item.hintTitle
        itemView?.item_title?.afterInput { text -> onEditText?.invoke(
                item.also { it.title = text },
                position
        )}

        itemView?.date?.text = item.getDateString()
    }

}
package name.marinchenko.partycalc.android.recycler.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.recycler.adapter.base.TouchAdapter
import name.marinchenko.partycalc.android.storage.session.Session
import name.marinchenko.partycalc.android.recycler.viewHolder.SessionViewHolder
import org.jetbrains.anko.layoutInflater

class SessionAdapter(ctx: Context): TouchAdapter<SessionViewHolder, Session>(ctx) {

    override fun addItem(item: Session) {
        list.add(0, item)
        notifyItemInserted(0)
        callback?.onAddItem(item, 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val holder = SessionViewHolder(ctx, ctx.layoutInflater.inflate(
                R.layout.session_item,
                parent,
                false
        ))
        return holder
                .onClickAction { item, position -> onItemClick?.invoke(item, position) }
                .onDragAction { onItemDrag?.invoke(it) }
                .onEditTextAction { item, position ->
                    if (!onBind) editItem(item, position, false)
                }
                as SessionViewHolder
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        onBindStart()
        holder.bind(list[position], position)
        onBindFinish()
    }

}

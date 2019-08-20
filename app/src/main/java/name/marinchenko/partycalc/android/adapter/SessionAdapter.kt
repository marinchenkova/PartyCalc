package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.base.DataObserverAdapter
import name.marinchenko.partycalc.android.adapter.base.ItemActionAdapter
import name.marinchenko.partycalc.android.storage.Session
import name.marinchenko.partycalc.android.viewHolder.SessionViewHolder
import org.jetbrains.anko.layoutInflater

class SessionAdapter(ctx: Context): DataObserverAdapter<SessionViewHolder, Session>(ctx) {

    private var onItemAdd: ((session: Session) -> Unit)? = null
    private var onItemEdit: ((session: Session) -> Unit)? = null
    private var onItemRemove: ((session: Session) -> Unit)? = null


    fun onItemAdd(action: (session: Session) -> Unit): SessionAdapter  {
        onItemAdd = action
        return this
    }

    fun onItemEdit(action: (session: Session) -> Unit): SessionAdapter  {
        onItemEdit = action
        return this
    }

    fun onItemRemove(action: (session: Session) -> Unit): SessionAdapter  {
        onItemRemove = action
        return this
    }

    override fun addItem(item: Session) {
        list.add(0, item)
        notifyItemInserted(0)
        onItemAdd?.invoke(item)
    }

    override fun editItem(item: Session, position: Int, notify: Boolean) {
        super.editItem(item, position, notify)
        onItemEdit?.invoke(item)
    }

    override fun removeItem(position: Int?) {
        if (position != null) onItemRemove?.invoke(list[position])
        super.removeItem(position)
    }

    override fun undoRemoveItem() {
        super.undoRemoveItem()
        onItemAdd?.invoke(lastRemovedItem)
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
                    if (!onBind) {
                        editItem(item, position, false)
                    }
                }
                as SessionViewHolder
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        onBindStart()
        holder.bind(list[position], position)
        onBindFinish()
    }

}

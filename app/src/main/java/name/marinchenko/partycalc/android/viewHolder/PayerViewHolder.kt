package name.marinchenko.partycalc.android.viewHolder

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.payer_item.view.*
import name.marinchenko.partycalc.android.adapter.PayerCheckAdapter
import name.marinchenko.partycalc.android.adapter.PayerCheckCompactAdapter
import name.marinchenko.partycalc.android.util.afterInput
import name.marinchenko.partycalc.android.util.isVisible
import name.marinchenko.partycalc.android.util.setVisible
import name.marinchenko.partycalc.android.viewHolder.base.AbstractItemViewHolder
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.PayerCheck


class PayerViewHolder(ctx: Context, view: View): AbstractItemViewHolder<Payer>(ctx, view) {

    private val adapter: PayerCheckAdapter
    private val adapterCompact = PayerCheckCompactAdapter(ctx)

    private var onCheck: ((check: PayerCheck, payerPosition: Int) -> Unit)? = null
    private var onExpand: ((isExpanded: Boolean, position: Int) -> Unit)? = null

    init {
        adapter = PayerCheckAdapter(ctx).onCheckAction { check, position, payerPosition ->
            onCheck?.invoke(check, payerPosition)
            adapterCompact.editItem(check, position)
        }

        itemView?.list_payer_checks?.adapter = adapter
        itemView?.list_payer_checks?.layoutManager = LinearLayoutManager(ctx)

        itemView?.list_payer_checks_compact?.adapter = adapterCompact
        itemView?.list_payer_checks_compact?.layoutManager = LinearLayoutManager(
                ctx,
                LinearLayoutManager.HORIZONTAL,
                false
        )
    }

    override fun bind(item: Payer, position: Int) {
        adapter.parentPosition = position

        itemView.setOnClickListener { onExpand?.invoke(expandView(), position) }
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

        itemView?.item_sum?.setText(item.sumString)
        itemView?.item_sum?.hint = item.hintSum
        itemView?.item_sum?.afterInput { text -> onEditText?.invoke(
                item.also { it.sumString = text },
                position
        )}

        updatePayerChecks(item)
        expandView(item.isExpanded)
    }

    fun onCheckAction(action: (check: PayerCheck, payerPosition: Int) -> Unit): PayerViewHolder {
        onCheck = action
        return this
    }

    fun onExpandAction(action: (isExpanded: Boolean, position: Int) -> Unit): PayerViewHolder {
        onExpand = action
        return this
    }

    private fun updatePayerChecks(payer: Payer) {
        adapter.updateList(payer.payerChecks)
        adapterCompact.updateList(payer.payerChecks)
    }

    private fun expandView(expanded: Boolean = !isExpanded()): Boolean {
        if (adapter.itemCount > 0) itemView?.list_payer_checks?.setVisible(expanded)
        return itemView?.list_payer_checks?.isVisible() ?: false
    }

    private fun isExpanded() = itemView?.list_payer_checks?.visibility == View.VISIBLE

}
package name.marinchenko.partycalc.android.viewHolder

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.payer_item.view.*
import name.marinchenko.partycalc.android.adapter.PayerCheckAdapter
import name.marinchenko.partycalc.android.adapter.PayerCheckCompactAdapter
import name.marinchenko.partycalc.android.util.afterInput
import name.marinchenko.partycalc.android.util.isVisible
import name.marinchenko.partycalc.android.util.listener.ItemEventListener
import name.marinchenko.partycalc.android.util.setVisibility
import name.marinchenko.partycalc.android.viewHolder.base.AbstractItemViewHolder
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.PayerCheck


class PayerViewHolder(
        ctx: Context,
        private var clickListener: ItemEventListener<Pair<Boolean, Int>>?,
        checkListener: ItemEventListener<Pair<PayerCheck, Int>>?,
        private var editTextListener: ItemEventListener<Pair<Payer, Int>>?,
        view: View
): AbstractItemViewHolder<Payer>(ctx, view) {

    private val adapter: PayerCheckAdapter
    private val adapterCompact = PayerCheckCompactAdapter(ctx)

    init {
        adapter = PayerCheckAdapter(ctx, 0,
                object : ItemEventListener<Triple<PayerCheck, Int, Int>> {
                    override fun onEvent(item: Triple<PayerCheck, Int, Int>) {
                        checkListener?.onEvent(Pair(item.first, item.third))
                        adapterCompact.editItem(item.first, item.second)
                    }
                }
        )

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

        itemView.setOnClickListener { clickListener?.onEvent(Pair(expandView(), position)) }

        itemView?.item_title?.setText(item.title)
        itemView?.item_title?.hint = item.hintTitle
        itemView?.item_title?.afterInput { text -> editTextListener?.onEvent(Pair(
                item.also { it.title = text },
                position
        ))}

        itemView?.item_sum?.setText(item.sumString)
        itemView?.item_sum?.hint = item.hintSum
        itemView?.item_sum?.afterInput { text -> editTextListener?.onEvent(Pair(
                item.also { it.sumString = text },
                position
        ))}

        updatePayerChecks(item)
        expandView(item.isExpanded)
    }

    private fun updatePayerChecks(payer: Payer) {
        adapter.updateList(payer.payerChecks)
        adapterCompact.updateList(payer.payerChecks)
    }

    private fun expandView(expanded: Boolean = !isExpanded()): Boolean {
        if (adapter.itemCount > 0) itemView?.list_payer_checks?.setVisibility(expanded)
        return itemView?.list_payer_checks?.isVisible() ?: false
    }

    private fun isExpanded() = itemView?.list_payer_checks?.visibility == View.VISIBLE

}
package name.marinchenko.partycalc.android.util.viewHolder

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.payer_item.view.*
import name.marinchenko.partycalc.android.adapter.PayerCheckAdapter
import name.marinchenko.partycalc.android.util.isVisible
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.android.util.setVisibility
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.PayerCheck


class PayerViewHolder(
        ctx: Context,
        private var clickListener: SimpleEventListener<Pair<Boolean, Int>>?,
        checkListener: SimpleEventListener<Pair<PayerCheck, Int>>?,
        view: View):
        AbstractItemViewHolder<Payer>(ctx, view) {

    private val adapter = PayerCheckAdapter(ctx, 0, checkListener)

    init {
        itemView?.list_payer_checks?.adapter = adapter
        itemView?.list_payer_checks?.layoutManager = LinearLayoutManager(ctx)
    }

    override fun bind(item: Payer, position: Int) {
        adapter.parentPosition = position

        itemView.setOnClickListener { clickListener?.onEvent(Pair(expandView(), position)) }

        itemView?.item_title?.setText(item.title)
        itemView?.item_title?.hint = item.hintTitle

        itemView?.item_sum?.setText(item.sumString)
        itemView?.item_sum?.hint = item.hintSum

        updatePayerChecks(item)
        expandView(item.isExpanded)
    }

    private fun updatePayerChecks(payer: Payer) {
        adapter.updateList(payer.payerChecks)
    }

    private fun expandView(expanded: Boolean = !isExpanded()): Boolean {
        if (adapter.itemCount > 0) itemView?.list_payer_checks?.setVisibility(expanded)
        return itemView?.list_payer_checks?.isVisible() ?: false
    }

    private fun isExpanded() = itemView?.list_payer_checks?.visibility == View.VISIBLE

}
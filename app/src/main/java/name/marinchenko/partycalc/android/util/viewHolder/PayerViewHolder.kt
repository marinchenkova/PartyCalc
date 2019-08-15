package name.marinchenko.partycalc.android.util.viewHolder

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import kotlinx.android.synthetic.main.payer_item.view.*
import name.marinchenko.partycalc.android.adapter.PayerCheckAdapter
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.PayerCheck


class PayerViewHolder(
        ctx: Context,
        checkListener: OnItemClickListener<Pair<PayerCheck, Int>>?,
        view: View):
        AbstractItemViewHolder<Payer>(ctx, view) {

    private val adapter = PayerCheckAdapter(ctx, 0, checkListener)

    init {
        itemView?.list_payer_checks?.adapter = adapter
        itemView?.list_payer_checks?.layoutManager = LinearLayoutManager(ctx)
    }

    override fun bind(item: Payer, position: Int) {
        adapter.parentPosition = position

        itemView.setOnClickListener { expandView() }

        itemView?.item_title?.setText(item.title)
        itemView?.item_title?.hint = item.hintTitle

        itemView?.item_sum?.setText(item.sumString)
        itemView?.item_sum?.hint = item.hintSum

        updatePayerChecks(item)
    }

    private fun updatePayerChecks(payer: Payer) {
        adapter.updateList(payer.payerChecks)
    }

    private fun expandView(expand: Boolean = !isExpanded()) {
        //TransitionManager.beginDelayedTransition(itemView?.card_view, ChangeBounds())
        itemView?.list_payer_checks?.visibility = if (expand) View.VISIBLE else View.GONE
    }

    private fun isExpanded() = itemView?.list_payer_checks?.visibility == View.VISIBLE

}
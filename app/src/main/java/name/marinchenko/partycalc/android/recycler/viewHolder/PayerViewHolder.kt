package name.marinchenko.partycalc.android.recycler.viewHolder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.payer_item.view.*
import name.marinchenko.partycalc.android.recycler.adapter.PayerCheckAdapter
import name.marinchenko.partycalc.android.recycler.adapter.PayerCheckCompactAdapter
import name.marinchenko.partycalc.android.recycler.viewHolder.base.AbstractItemViewHolder
import name.marinchenko.partycalc.android.util.afterInput
import name.marinchenko.partycalc.android.util.afterTextChanged
import name.marinchenko.partycalc.android.util.isVisible
import name.marinchenko.partycalc.android.util.setVisible
import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.formatDouble
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

        itemView.list_payer_checks?.adapter = adapter
        itemView.list_payer_checks?.layoutManager = LinearLayoutManager(ctx)

        itemView.list_payer_checks_compact?.adapter = adapterCompact
        itemView.list_payer_checks_compact?.layoutManager = LinearLayoutManager(
                ctx,
                LinearLayoutManager.HORIZONTAL,
                false
        )
    }

    override fun bind(item: Payer, position: Int) {
        adapter.parentPosition = position

        itemView.expander?.setOnClickListener {
            onExpand?.invoke(expandView(), position)
            //animateExpander(isExpanded())
        }

        itemView.item_title?.setText(item.title)
        itemView.item_title?.hint = item.hintTitle
        itemView.item_title?.afterInput { text -> onEditText?.invoke(
                item.also { it.title = text.trim() },
                position
        )}

        itemView.item_sum?.setText(item.sumString)
        itemView.item_sum?.hint = item.hintSum
        itemView.item_sum?.afterInput { text -> onEditText?.invoke(
                item.also { it.sumString = text.trim() },
                position
        )}

        itemView.equals?.hint = "= ${item.availableSum()}"
        itemView.item_sum?.afterTextChanged { text ->
            itemView.equals?.hint = "= ${formatDouble(PartyCalc.parseSumString(text))}"
        }

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
        adapter.update(payer.payerChecks)
        adapterCompact.updateList(payer.payerChecks)
    }

    private fun expandView(expanded: Boolean = !isExpanded()): Boolean {
        if (adapter.itemCount > 0) {
            itemView.list_payer_checks?.setVisible(expanded)
            animateExpander(expanded)
        }
        return itemView.list_payer_checks?.isVisible() ?: false
    }

    private fun isExpanded() = itemView.list_payer_checks?.visibility == View.VISIBLE

    private fun animateExpander(expand: Boolean) {
        val rotation = if (expand) 180f else 0f
        itemView.expander?.animate()?.rotation(rotation)?.setDuration(200)?.start()
    }



}
package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.android.util.itemFactory.PayerFactory
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.PayerViewHolder
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.PayerCheck
import org.jetbrains.anko.layoutInflater


class PayerAdapter(ctx: Context): AbstractItemAdapter<PayerViewHolder, Payer>(ctx) {

    private val clickListener = object : OnItemClickListener<Pair<Boolean, Int>> {
        override fun onItemClick(item: Pair<Boolean, Int>) {
            list[item.second].isExpanded = item.first
        }
    }

    private val checkListener = object : OnItemClickListener<Pair<PayerCheck, Int>> {
        override fun onItemClick(item: Pair<PayerCheck, Int>) {
            onPayerCheckClick(item.first, item.second)
        }
    }

    override val factory: ItemFactory<Payer>
        get() = PayerFactory(ctx)


    private fun onPayerCheckClick(check: PayerCheck, payerPosition: Int) {
        list[payerPosition].payerChecks.find { it.id == check.id }?.isChecked = check.isChecked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerViewHolder {
        return PayerViewHolder(ctx, clickListener, checkListener, ctx.layoutInflater.inflate(
                R.layout.payer_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: PayerViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}
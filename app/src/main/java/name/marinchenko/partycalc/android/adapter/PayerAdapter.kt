package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.base.UndoRemoveItemAdapter
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.android.util.itemFactory.PayerFactory
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.android.util.viewHolder.PayerViewHolder
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.PayerCheck
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater


class PayerAdapter(ctx: Context): UndoRemoveItemAdapter<PayerViewHolder, Payer>(ctx) {

    private val clickListener = object : SimpleEventListener<Pair<Boolean, Int>> {
        override fun onEvent(item: Pair<Boolean, Int>) {
            list[item.second].isExpanded = item.first
        }
    }

    private val checkListener = object : SimpleEventListener<Pair<PayerCheck, Int>> {
        override fun onEvent(item: Pair<PayerCheck, Int>) {
            onPayerCheckClick(item.first, item.second)
        }
    }

    override val factory: ItemFactory<Payer>
        get() = PayerFactory(ctx)

    private var products = mutableListOf<Product>()


    override fun newItem() {
        super.newItem()
        updatePayerChecks(products)
    }

    fun productsWereUpdated(update: List<Product>) {
        products.clear()
        products.addAll(update)
        updatePayerChecks(update)
    }

    private fun updatePayerChecks(update: List<Product>) {
        list.forEach { it.updatePayerChecks(update) }
        notifyDataSetChanged()
    }

    private fun onPayerCheckClick(check: PayerCheck, payerPosition: Int) {
        list[payerPosition].payerChecks.find {
            it.product.id == check.product.id
        }?.isChecked = check.isChecked
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
package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.base.DataChangeObserverAdapter
import name.marinchenko.partycalc.android.util.item.ItemFactory
import name.marinchenko.partycalc.android.util.item.PayerFactory
import name.marinchenko.partycalc.android.util.listener.ItemEventListener
import name.marinchenko.partycalc.android.viewHolder.PayerViewHolder
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.PayerCheck
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater


class PayerAdapter(
        ctx: Context,
        listListener: ItemEventListener<List<Payer>>? = null
): DataChangeObserverAdapter<PayerViewHolder, Payer>(ctx, listListener) {

    private val clickListener = object : ItemEventListener<Pair<Boolean, Int>> {
        override fun onEvent(item: Pair<Boolean, Int>) {
            setExpanded(item.first, item.second)
        }
    }

    private val editTextListener = object : ItemEventListener<Pair<Payer, Int>> {
        override fun onEvent(item: Pair<Payer, Int>) {
            if (!onBind) {
                editItem(item.first, item.second, false)
                observer.onChanged()
            }
        }
    }

    private val checkListener = object : ItemEventListener<Pair<PayerCheck, Int>> {
        override fun onEvent(item: Pair<PayerCheck, Int>) {
            onPayerCheckClick(item.first, item.second)
            observer.onChanged()
        }
    }

    override val factory: ItemFactory<Payer>
        get() = PayerFactory(ctx)

    private var products = mutableListOf<Product>()


    override fun newItem() {
        super.newItem()
        updatePayerChecks(products)
    }

    private fun setExpanded(expanded: Boolean, position: Int) {
        list[position].isExpanded = expanded
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
        return PayerViewHolder(
                ctx,
                clickListener,
                checkListener,
                editTextListener,
                ctx.layoutInflater.inflate(
                        R.layout.payer_item,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: PayerViewHolder, position: Int) {
        onBindStart()
        holder.bind(list[position], position)
        onBindFinish()
    }
}
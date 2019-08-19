package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.base.DataObserverAdapter
import name.marinchenko.partycalc.android.adapter.base.NewItemAdapter
import name.marinchenko.partycalc.android.storage.getPayerCheckDefaultState
import name.marinchenko.partycalc.android.util.item.ItemFactory
import name.marinchenko.partycalc.android.util.item.PayerFactory
import name.marinchenko.partycalc.android.viewHolder.PayerViewHolder
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.PayerCheck
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.runOnUiThread


class PayerAdapter(ctx: Context): NewItemAdapter<PayerViewHolder, Payer>(ctx) {

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

    @Synchronized fun productsWereUpdated(update: List<Product>) {
        products.clear()
        products.addAll(update)
        ctx.runOnUiThread { updatePayerChecks(update) }
    }

    private fun updatePayerChecks(update: List<Product>) {
        list.forEach { it.updatePayerChecks(update, ctx.getPayerCheckDefaultState()) }
        notifyDataSetChanged()
    }

    private fun onPayerCheckClick(check: PayerCheck, payerPosition: Int) {
        list[payerPosition].payerChecks.find {
            it.product.id == check.product.id
        }?.isChecked = check.isChecked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerViewHolder {
        val holder = PayerViewHolder(ctx, ctx.layoutInflater.inflate(
                R.layout.payer_item,
                parent,
                false
        ))
        return holder
                .onCheckAction { check, payerPosition ->
                    onPayerCheckClick(check, payerPosition)
                    observer.onChanged()
                }
                .onExpandAction { isExpanded, position -> setExpanded(isExpanded, position) }
                .onEditTextAction { item, position ->
                    if (!onBind) {
                        editItem(item, position, false)
                        observer.onChanged()
                    }
                }
                .onDragAction { onItemDrag?.invoke(it) }
                as PayerViewHolder
    }

    override fun onBindViewHolder(holder: PayerViewHolder, position: Int) {
        onBindStart()
        holder.bind(list[position], position)
        onBindFinish()
    }
}
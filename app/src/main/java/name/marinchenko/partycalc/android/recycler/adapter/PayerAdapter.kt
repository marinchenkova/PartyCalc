package name.marinchenko.partycalc.android.recycler.adapter

import android.content.Context
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.recycler.adapter.base.IdItemAdapter
import name.marinchenko.partycalc.android.recycler.adapter.base.TouchAdapter
import name.marinchenko.partycalc.android.storage.getPayerCheckDefaultState
import name.marinchenko.partycalc.android.recycler.factory.PayerFactory
import name.marinchenko.partycalc.android.recycler.viewHolder.PayerViewHolder
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.PayerCheck
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.layoutInflater


class PayerAdapter(ctx: Context): TouchAdapter<PayerViewHolder, Payer>(ctx) {

    private val factory = PayerFactory(ctx)
    private var loadProducts: (() -> List<Product>)? = null
    private var onExpand: ((item: Payer, position: Int) -> Unit)? = null


    fun onExpandAction(action: (item: Payer, position: Int) -> Unit): PayerAdapter {
        onExpand = action
        return this
    }

    val productCallback = object : IdItemAdapter.Callback<Product> {
        override fun onAddItem(item: Product, position: Int, undoRemove: Boolean) {
            list.forEach {
                it.addPayerCheck(item, position, ctx.getPayerCheckDefaultState(), undoRemove)
            }
            notifyDataSetChanged()
            callback?.onUpdateList(list)
        }

        override fun onRemoveItem(item: Product, position: Int) {
            list.forEach { it.removePayerCheck(item) }
            notifyDataSetChanged()
            callback?.onUpdateList(list)
        }

        override fun onEditItem(item: Product, position: Int) {
            list.forEach { it.editPayerCheck(item) }
            notifyDataSetChanged()
            callback?.onUpdateList(list)
        }

        override fun onUpdateList(new: List<Product>) {}
    }


    fun onLoadProducts(action: () -> List<Product>): PayerAdapter {
        loadProducts = action
        return this
    }

    fun newItem() {
        val item = factory.nextItem(
                list.map { it.num }.toHashSet(),
                list.map { it.id }.toHashSet()
        )
        val products = loadProducts?.invoke()
        if (products != null) item.newPayerChecks(products, ctx.getPayerCheckDefaultState())
        addItem(item)
    }

    private fun setExpanded(expanded: Boolean, position: Int) {
        list[position].isExpanded = expanded
        onExpand?.invoke(list[position], position)
    }

    private fun onPayerCheckClick(check: PayerCheck, payerPosition: Int) {
        list[payerPosition].payerChecks.find {
            it.product.id == check.product.id
        }?.isChecked = check.isChecked
        callback?.onEditItem(list[payerPosition], payerPosition)
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
                }
                .onExpandAction { isExpanded, position -> setExpanded(isExpanded, position) }
                .onEditTextAction { item, position ->
                    if (!onBind) editItem(item, position, false)
                }
                as PayerViewHolder
    }

    override fun onBindViewHolder(holder: PayerViewHolder, position: Int) {
        onBindStart()
        holder.bind(list[position], position)
        onBindFinish()
    }

}
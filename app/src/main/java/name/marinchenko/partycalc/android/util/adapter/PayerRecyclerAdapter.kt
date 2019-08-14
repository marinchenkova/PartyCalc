package name.marinchenko.partycalc.android.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.itemFactory.ItemFactory
import name.marinchenko.partycalc.android.util.itemFactory.PayerFactory
import name.marinchenko.partycalc.android.util.itemFactory.ProductFactory
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.PayerViewHolder
import org.jetbrains.anko.layoutInflater

class PayerRecyclerAdapter(
        ctx: Context,
        clickListener: OnItemClickListener
) : ItemRecyclerAdapter<PayerViewHolder>(
        ctx,
        R.layout.payer_item,
        clickListener
) {

    override val factory: ItemFactory
        get() = PayerFactory(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerViewHolder {
        return PayerViewHolder(ctx.layoutInflater.inflate(
                itemLayout,
                parent,
                false
        ))
    }

}
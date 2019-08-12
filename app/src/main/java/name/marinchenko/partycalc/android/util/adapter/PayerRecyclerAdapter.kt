package name.marinchenko.partycalc.android.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.ItemViewHolder
import name.marinchenko.partycalc.android.util.viewHolder.PayerViewHolder
import name.marinchenko.partycalc.android.util.viewHolder.ProductViewHolder

class PayerRecyclerAdapter(
        inflater: LayoutInflater,
        listener: OnItemClickListener
) : ItemRecyclerAdapter<PayerViewHolder>(
        inflater,
        R.layout.payer_item,
        listener
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerViewHolder {
        return PayerViewHolder(inflater.inflate(
                itemLayout,
                parent,
                false
        ))
    }

}
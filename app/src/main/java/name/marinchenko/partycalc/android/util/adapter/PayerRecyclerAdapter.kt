package name.marinchenko.partycalc.android.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.PayerViewHolder

class PayerRecyclerAdapter(
        inflater: LayoutInflater,
        clickListener: OnItemClickListener
) : ItemRecyclerAdapter<PayerViewHolder>(
        inflater,
        R.layout.payer_item,
        clickListener
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerViewHolder {
        return PayerViewHolder(inflater.inflate(
                itemLayout,
                parent,
                false
        ))
    }

}
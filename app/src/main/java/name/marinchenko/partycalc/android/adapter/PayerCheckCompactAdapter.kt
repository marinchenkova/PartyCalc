package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.dpToPixels
import name.marinchenko.partycalc.android.util.viewHolder.PayerCheckCompactViewHolder
import name.marinchenko.partycalc.core.item.PayerCheck
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.layoutInflater


const val PARENT_MARGIN = 32


class PayerCheckCompactAdapter(private val ctx: Context):
        RecyclerView.Adapter<PayerCheckCompactViewHolder>() {

    private val list = mutableListOf<PayerCheck>()


    fun updateList(new: Set<PayerCheck>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    fun editItem(new: PayerCheck, position: Int) {
        list[position] = new
        notifyItemChanged(position)
    }

    private fun calculateItemWidth(): Float {
        val viewWidth = ctx.displayMetrics.widthPixels - ctx.dpToPixels(PARENT_MARGIN)
        return viewWidth / itemCount
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerCheckCompactViewHolder {

        return PayerCheckCompactViewHolder(ctx, ctx.layoutInflater.inflate(
                R.layout.payer_check_compact_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: PayerCheckCompactViewHolder, position: Int) {
        holder.bind(list[position], calculateItemWidth())
    }

}
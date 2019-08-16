package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.adapter.base.SimpleAdapter
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.android.util.viewHolder.PayerCheckViewHolder
import name.marinchenko.partycalc.core.item.PayerCheck
import org.jetbrains.anko.layoutInflater

class PayerCheckAdapter(
        private val ctx: Context,
        var parentPosition: Int,
        private val listener: SimpleEventListener<Triple<PayerCheck, Int, Int>>? = null
): RecyclerView.Adapter<PayerCheckViewHolder>() {

    private val list = mutableListOf<PayerCheck>()


    fun updateList(new: Set<PayerCheck>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerCheckViewHolder {
        return PayerCheckViewHolder(listener, ctx.layoutInflater.inflate(
                R.layout.payer_check_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: PayerCheckViewHolder, position: Int) {
        holder.bind(list[position], position, parentPosition)
    }


}
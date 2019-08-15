package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.PayerCheckViewHolder
import name.marinchenko.partycalc.core.item.PayerCheck
import org.jetbrains.anko.layoutInflater

class PayerCheckAdapter(
        private val ctx: Context,
        var parentPosition: Int,
        private val listener: OnItemClickListener<Pair<PayerCheck, Int>>? = null
): RecyclerView.Adapter<PayerCheckViewHolder>(), SimpleItemAdapter<PayerCheck> {

    private val list = mutableListOf<PayerCheck>()


    fun updateList(new: Set<PayerCheck>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    fun editItem(position: Int, update: PayerCheck) {
        list[position] = update
        notifyDataSetChanged()
    }

    fun editItem(position: Int, update: String) {
        list[position].title = update
        notifyDataSetChanged()
    }

    override fun addItem(item: PayerCheck) {
        list.add(item)
        notifyDataSetChanged()
    }

    override fun removeItem(position: Int?) {
        position ?: return
        list.removeAt(position)
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
        holder.bind(list[position], parentPosition)
    }


}
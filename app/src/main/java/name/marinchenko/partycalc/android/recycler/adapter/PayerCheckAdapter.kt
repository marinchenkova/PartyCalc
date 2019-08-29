package name.marinchenko.partycalc.android.recycler.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.recycler.viewHolder.PayerCheckViewHolder
import name.marinchenko.partycalc.core.item.PayerCheck
import org.jetbrains.anko.layoutInflater

class PayerCheckAdapter(private val ctx: Context, var parentPosition: Int = 0):
        RecyclerView.Adapter<PayerCheckViewHolder>() {

    private val list = mutableListOf<PayerCheck>()
    private var onCheck: ((check: PayerCheck, position: Int, payerPosition: Int) -> Unit)? = null

    fun update(new: List<PayerCheck>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    fun onCheckAction(action: (check: PayerCheck,
                               position: Int,
                               payerPosition: Int) -> Unit): PayerCheckAdapter {
        onCheck = action
        return this
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerCheckViewHolder {
        val holder = PayerCheckViewHolder(ctx.layoutInflater.inflate(
                R.layout.payer_check_item,
                parent,
                false
        ))

        return holder.onCheckAction { check, position, payerPosition ->
            onCheck?.invoke(check, position, payerPosition)
        }
    }

    override fun onBindViewHolder(holder: PayerCheckViewHolder, position: Int) {
        holder.bind(list[position], position, parentPosition)
    }


}
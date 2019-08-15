package name.marinchenko.partycalc.android.util.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.payer_check_item.view.*
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.core.item.PayerCheck

class PayerCheckViewHolder(private val listener: OnItemClickListener<Pair<PayerCheck, Int>>?, view: View?):
        RecyclerView.ViewHolder(view), BinderViewHolder<PayerCheck> {

    override fun bind(item: PayerCheck, position: Int) {
        itemView?.check_box?.setOnClickListener { listener?.onItemClick(Pair(item, position)) }
        itemView?.check_box?.isChecked = item.isChecked
        itemView?.check_title?.text = item.title
    }

}
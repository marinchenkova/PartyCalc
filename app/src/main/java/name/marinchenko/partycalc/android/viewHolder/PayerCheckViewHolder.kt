package name.marinchenko.partycalc.android.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.payer_check_item.view.*
import name.marinchenko.partycalc.core.item.PayerCheck
import org.jetbrains.anko.backgroundColor

class PayerCheckViewHolder(view: View?): RecyclerView.ViewHolder(view) {

    private var onCheck: ((check: PayerCheck, position: Int, payerPosition: Int) -> Unit)? = null

    fun bind(item: PayerCheck, position: Int, parentPosition: Int) {
        itemView?.check_title?.text = item.product.title
        itemView?.check_title?.hint = item.product.hintTitle
        itemView?.check_box?.isChecked = item.isChecked
        itemView?.check_box?.setOnClickListener { onCheck?.invoke(
                item.also { it.isChecked = itemView.check_box?.isChecked ?: false},
                position,
                parentPosition)
        }

        itemView.backgroundColor = item.product.color
    }

    fun onCheckAction(action: (check: PayerCheck,
                               position: Int,
                               payerPosition: Int) -> Unit): PayerCheckViewHolder {
        onCheck = action
        return this
    }

}
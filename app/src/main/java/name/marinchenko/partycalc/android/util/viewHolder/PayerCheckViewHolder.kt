package name.marinchenko.partycalc.android.util.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.payer_check_item.view.*
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.core.item.PayerCheck
import org.jetbrains.anko.backgroundColor

class PayerCheckViewHolder(
        private val listener: SimpleEventListener<Triple<PayerCheck, Int, Int>>?,
        view: View?
): RecyclerView.ViewHolder(view) {

    fun bind(item: PayerCheck, position: Int, parentPosition: Int) {
        itemView?.check_title?.text = item.product.title
        itemView?.check_title?.hint = item.product.hintTitle
        itemView?.check_box?.isChecked = item.isChecked
        itemView?.check_box?.setOnClickListener { listener?.onEvent(Triple(
                item.also { it.isChecked = itemView.check_box?.isChecked ?: false},
                position,
                parentPosition
        ))}

        itemView.backgroundColor = item.product.color
    }

}
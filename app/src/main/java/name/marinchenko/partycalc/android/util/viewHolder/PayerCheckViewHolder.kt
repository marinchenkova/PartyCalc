package name.marinchenko.partycalc.android.util.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.payer_check_item.view.*
import name.marinchenko.partycalc.android.util.listener.SimpleEventListener
import name.marinchenko.partycalc.core.item.PayerCheck
import org.jetbrains.anko.backgroundColor

class PayerCheckViewHolder(
        private val listener: SimpleEventListener<Pair<PayerCheck, Int>>?,
        view: View?
): RecyclerView.ViewHolder(view), BinderViewHolder<PayerCheck> {

    override fun bind(item: PayerCheck, position: Int) {
        val title = if (item.product.title.isEmpty()) item.product.hintTitle
        else item.product.title

        itemView?.check_title?.text = title
        itemView?.check_box?.isChecked = item.isChecked
        itemView?.check_box?.setOnClickListener { listener?.onEvent(Pair(
                item.also { it.isChecked = itemView.check_box?.isChecked ?: false},
                position
        ))}

        itemView.backgroundColor = item.product.color
    }

}
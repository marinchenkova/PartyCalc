package name.marinchenko.partycalc.android.util.viewHolder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.payer_check_compact_item.view.*
import name.marinchenko.partycalc.android.util.dpToPixels
import name.marinchenko.partycalc.android.util.setBorderOfColor
import name.marinchenko.partycalc.core.item.PayerCheck
import org.jetbrains.anko.backgroundColor


const val BORDER_WIDTH_DP = 2


class PayerCheckCompactViewHolder(
        private val ctx: Context,
        view: View?
): RecyclerView.ViewHolder(view) {

    fun bind(item: PayerCheck, itemWidth: Float) {
        itemView.layoutParams.width = itemWidth.toInt()
        if (item.isChecked) itemView?.back?.backgroundColor =  item.product.color
        else itemView?.back?.setBorderOfColor(
                item.product.color,
                ctx.dpToPixels(BORDER_WIDTH_DP).toInt()
        )
    }

}
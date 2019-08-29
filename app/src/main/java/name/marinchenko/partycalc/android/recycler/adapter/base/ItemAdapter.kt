package name.marinchenko.partycalc.android.recycler.adapter.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import name.marinchenko.partycalc.android.storage.getPreferredProductHint
import name.marinchenko.partycalc.android.storage.getPreferredSumHint
import name.marinchenko.partycalc.android.storage.getShowSumHints
import name.marinchenko.partycalc.core.item.Item
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

abstract class ItemAdapter<VH: RecyclerView.ViewHolder, I: Item>(ctx: Context) :
        TouchAdapter<VH, I>(ctx) {

    private var onChecked: (() -> Unit)? = null


    fun onChecked(action: () -> Unit): ItemAdapter<*,*> {
        onChecked = action
        return this
    }

    fun checkShowHints() {
        ctx.doAsync {
            list.forEach {
                it.hintTitle = ctx.getPreferredProductHint(it.num)
                it.hintSum = if (it.hintSum.isNotEmpty() && ctx.getShowSumHints()) it.hintSum
                else ctx.getPreferredSumHint()
            }
            uiThread {
                notifyDataSetChanged()
                onChecked?.invoke()
            }
        }
    }

}
package name.marinchenko.partycalc.android.recycler.adapter.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import name.marinchenko.partycalc.android.storage.checkShowHints
import name.marinchenko.partycalc.core.item.Item
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

private const val NUM_EN = 26
private const val NUM_RU = 33

abstract class ItemAdapter<VH: RecyclerView.ViewHolder, I: Item>(ctx: Context):
        TouchAdapter<VH, I>(ctx) {

    private var onEaster: ((ok: Boolean) -> Unit)? = null


    fun checkShowHints() {
        ctx.checkShowHints(list)
        notifyDataSetChanged()
    }

    fun onEaster(action: (ok: Boolean) -> Unit): ItemAdapter<*,*> {
        onEaster = action
        return this
    }

    override fun addItem(item: I) {
        super.addItem(item)
        checkEaster()
    }

    override fun removeItem(position: Int?) {
        super.removeItem(position)
        checkEaster()
    }

    private fun checkEaster() {
        val listCopy = list
        ctx.doAsync {
            val num = when (Locale.getDefault().language) {
                "ru" -> NUM_RU
                else -> NUM_EN
            }

            if (listCopy.size < num) {
                uiThread { onEaster?.invoke(false) }
                return@doAsync
            }

            val nums = listCopy.map { it.num }
            val required = (0 until num).toList()

            if (nums == required) uiThread { onEaster?.invoke(true) }
            else uiThread { onEaster?.invoke(false) }
        }
    }

}
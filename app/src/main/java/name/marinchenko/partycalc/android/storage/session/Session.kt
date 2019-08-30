package name.marinchenko.partycalc.android.storage.session

import android.content.Context
import name.marinchenko.partycalc.android.storage.getPreferredProductHint
import name.marinchenko.partycalc.android.storage.getPreferredSumHint
import name.marinchenko.partycalc.android.storage.getShowSumHints
import name.marinchenko.partycalc.android.util.formatFull
import name.marinchenko.partycalc.core.item.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*


const val SESSION_ID = "session_id"


data class Session(
        override val id: Long,
        var title: String,
        val hintTitle: String,
        val date: Date,
        var products: List<Product>,
        var payers: List<Payer>,
        var results: List<Result>
) : IdItem {
    
    fun getDateString() = date.formatFull()

    fun getAvailableTitle() = if (title.isEmpty()) hintTitle else title

    fun checkShowHints(ctx: Context) {
        checkShowHints(products, ctx)
        checkShowHints(payers, ctx)
        results.forEach {
            checkShowHints(it.who, ctx)
            checkShowHints(it.toWhom, ctx)
        }
    }

    private fun checkShowHints(list: List<Item>, ctx: Context) {
        list.forEach { checkShowHints(it, ctx) }
    }

    private fun checkShowHints(item: Item, ctx: Context) {
        item.hintTitle = ctx.getPreferredProductHint(item.num)
        item.hintSum = if (item.hintSum.isNotEmpty() && ctx.getShowSumHints()) item.hintSum
        else ctx.getPreferredSumHint()
    }

}
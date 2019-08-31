package name.marinchenko.partycalc.android.storage.session

import android.content.Context
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.storage.checkShowHints
import name.marinchenko.partycalc.android.storage.getPreferredProductHint
import name.marinchenko.partycalc.android.storage.getPreferredSumHint
import name.marinchenko.partycalc.android.storage.getShowSumHints
import name.marinchenko.partycalc.android.util.formatDayMonthYear
import name.marinchenko.partycalc.android.util.formatFull
import name.marinchenko.partycalc.core.item.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*


const val SESSION_ID = "session_id"


data class Session(
        override val id: Long,
        var title: String,
        var hintTitle: String,
        val date: Date,
        var products: List<Product>,
        var payers: List<Payer>,
        var results: List<Result>
) : IdItem {
    
    fun getDateString() = date.formatFull()

    fun getAvailableTitle() = if (title.isEmpty()) hintTitle else title

    fun checkHintTitle(ctx: Context) {
        hintTitle = "${ctx.getString(R.string.session_default_name)} ${date.formatDayMonthYear()}"
    }

    fun checkShowHints(ctx: Context) {

        ctx.checkShowHints(products)
        ctx.checkShowHints(payers)
        results.forEach {
            ctx.checkShowHints(it.who)
            ctx.checkShowHints(it.toWhom)
        }
    }

}
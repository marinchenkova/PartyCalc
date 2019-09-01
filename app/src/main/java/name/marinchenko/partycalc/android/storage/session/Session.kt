package name.marinchenko.partycalc.android.storage.session

import android.content.Context
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.storage.checkShowHintsPayers
import name.marinchenko.partycalc.android.storage.checkShowHintsProducts
import name.marinchenko.partycalc.android.util.formatDayMonth
import name.marinchenko.partycalc.android.util.formatFull
import name.marinchenko.partycalc.core.item.*
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
        hintTitle = "${ctx.getString(R.string.session_default_name)} ${date.formatDayMonth()}"
    }

    fun checkShowHints(ctx: Context) {
        ctx.checkShowHintsProducts(products)
        ctx.checkShowHintsPayers(payers)
        results.forEach {
            ctx.checkShowHintsPayers(it.who)
            ctx.checkShowHintsPayers(it.toWhom)
        }
    }

}
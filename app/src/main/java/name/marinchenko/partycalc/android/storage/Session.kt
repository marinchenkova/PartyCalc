package name.marinchenko.partycalc.android.storage

import name.marinchenko.partycalc.android.util.formatFull
import name.marinchenko.partycalc.core.item.IdItem
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product
import java.util.*


const val SESSION_ID = "session_id"


data class Session(
        override val id: Long,
        var title: String,
        val hintTitle: String,
        val date: Date,
        var products: List<Product>,
        var payers: List<Payer>
) : IdItem {

    fun getDateString() = date.formatFull()

}
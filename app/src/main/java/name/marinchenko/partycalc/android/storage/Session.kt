package name.marinchenko.partycalc.android.storage

import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product
import java.util.*


const val SESSION_ID = "session_id"


data class Session(
        val id: Long,
        val title: String,
        val hintTitle: String,
        val date: Date,
        val products: List<Product>,
        val payers: List<Payer>
)
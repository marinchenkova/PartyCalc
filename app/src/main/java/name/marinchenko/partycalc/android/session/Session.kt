package name.marinchenko.partycalc.android.session

import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product

data class Session(
        val id: SessionId,
        val title: String,
        val label: String,
        val products: List<Product>,
        val payers: List<Payer>
)
package name.marinchenko.partycalc.core.item

data class PayerCheck(
        val id: Long,
        var title: String = "",
        var isChecked: Boolean = false
)
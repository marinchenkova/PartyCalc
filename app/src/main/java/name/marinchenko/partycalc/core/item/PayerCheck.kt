package name.marinchenko.partycalc.core.item

data class PayerCheck(
        val product: Product,
        var isChecked: Boolean = false
) : Textable {

    override fun toText() = "${signText()} ${product.getAvailableTitle()}"

    private fun signText() = if (isChecked) "+" else "-"

    fun update(new: Product): PayerCheck {
        product.sumString = new.sumString
        product.title = new.title
        return this
    }

}
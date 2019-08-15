package name.marinchenko.partycalc.core.item

class Payer(
        id: Long,
        hintTitle: String,
        hintSum: String,
        num: Int,
        var isExpanded: Boolean = false
): Item(
        id,
        hintTitle,
        hintSum,
        num
) {

    val payerChecks = mutableSetOf<PayerCheck>()


    fun updatePayerChecks(products: List<Product>) {
        val newSet = products.mapNotNull { prod ->
            val checkForUpdate = payerChecks.find { check ->
                check.product.id == prod.id
            }
            if (checkForUpdate == null) return@mapNotNull PayerCheck(prod)
            else return@mapNotNull checkForUpdate.update(prod)
        }.toSet()
        payerChecks.clear()
        payerChecks.addAll(newSet)
    }

    override fun toString() = "Payer(id=$id, hintTitle=$hintTitle, hintSum=$hintSum, " +
            "num=$num, checks=$payerChecks)"

}
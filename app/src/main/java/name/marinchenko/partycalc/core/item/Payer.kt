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
    private var lastRemoved = mutableSetOf<PayerCheck>()

    fun updatePayerChecks(products: List<Product>) {
        val newSet = products.map { prod ->
            val forUpdate = payerChecks.find { check -> check.product.id == prod.id }
            if (forUpdate == null) {
                val undo = lastRemoved.find { check -> check.product.id == prod.id }
                if (undo == null) return@map PayerCheck(prod)
                else return@map undo.update(prod)
            }
            else return@map forUpdate.update(prod)
        }.toSet()

        saveRemoved(newSet)

        payerChecks.clear()
        payerChecks.addAll(newSet)
    }

    private fun saveRemoved(newSet: Set<PayerCheck>) {
        val oldSet = payerChecks
        oldSet.removeAll(newSet)
        lastRemoved.clear()
        lastRemoved.addAll(oldSet)
    }

    override fun toString() = "Payer(id=$id, hintTitle=$hintTitle, hintSum=$hintSum, " +
            "num=$num, checks=$payerChecks)"

}
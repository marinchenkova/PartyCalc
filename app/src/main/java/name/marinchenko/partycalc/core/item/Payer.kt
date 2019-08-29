package name.marinchenko.partycalc.core.item

import name.marinchenko.partycalc.android.util.swapItems
import name.marinchenko.partycalc.core.formatDouble

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

    val payerChecks = mutableListOf<PayerCheck>()
    private var lastRemovedCheck = false


    fun addPayerCheck(product: Product, position: Int, isChecked: Boolean, undoRemove: Boolean) {
        if (undoRemove) payerChecks.add(position, PayerCheck(product, lastRemovedCheck))
        else payerChecks.add(position, PayerCheck(product, isChecked))
    }

    fun removePayerCheck(product: Product) {
        val toRemove = payerChecks.find { product == it.product }
        if (toRemove != null) {
            payerChecks.remove(toRemove)
            lastRemovedCheck = toRemove.isChecked
        }
    }

    fun editPayerCheck(product: Product) {
        payerChecks.find { it.product.id == product.id }?.update(product)
    }

    fun newPayerChecks(new: List<Product>, isChecked: Boolean) {
        payerChecks.clear()
        payerChecks.addAll(new.map { PayerCheck(it, isChecked) })
    }

    override fun toText() = "${getAvailableTitle()} payed ${formatDouble(sum())} for:\n" +
            payerChecks.filter { it.isChecked }.joinToString("\n") { it.toText() }

    override fun toString() = "Payer(str=$id, hintTitle=$hintTitle, hintSum=$hintSum, " +
            "num=$num, checks=$payerChecks)"

}
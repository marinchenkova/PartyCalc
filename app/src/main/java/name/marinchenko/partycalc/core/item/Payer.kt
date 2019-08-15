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

    val payerChecks = mutableSetOf<PayerCheck>(
            PayerCheck(0, "Stub0"),
            PayerCheck(1, "Stub1", true)
    )

    override fun toString() = "Payer(id=$id, hintTitle=$hintTitle, hintSum=$hintSum, num=$num, checks=$payerChecks)"
}
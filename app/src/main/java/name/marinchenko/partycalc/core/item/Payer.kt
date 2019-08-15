package name.marinchenko.partycalc.core.item

class Payer(
        id: Long,
        hintTitle: String,
        hintSum: String,
        num: Int
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

}
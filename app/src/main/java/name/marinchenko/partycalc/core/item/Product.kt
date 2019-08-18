package name.marinchenko.partycalc.core.item

import name.marinchenko.partycalc.core.formatDouble

class Product(
        id: Long,
        hintTitle: String,
        hintSum: String,
        val color: Int,
        num: Int
): Item(
        id,
        hintTitle,
        hintSum,
        num
) {

    override fun toText() = "${getAvailableTitle()} for ${formatDouble(sum())}"

}

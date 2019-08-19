package name.marinchenko.partycalc.core.item

import name.marinchenko.partycalc.core.formatDouble

data class Result(
        val who: Payer,
        val toWhom: Payer,
        val sum: Double
): Textable {

    var isDone = false
    var id: Long = 0

    override fun toText() = "${who.getAvailableTitle()}: ${formatDouble(sum)} -> ${toWhom.getAvailableTitle()}"

}
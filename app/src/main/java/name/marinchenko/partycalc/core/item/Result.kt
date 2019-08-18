package name.marinchenko.partycalc.core.item

import name.marinchenko.partycalc.core.formatDouble

data class Result(
        val who: Payer,
        val toWhom: Payer,
        val sum: Double
): Textable {

    override fun toText() = "${who.getAvailableTitle()}: ${formatDouble(sum)} -> ${toWhom.getAvailableTitle()}, ${isDoneText()}"

    private fun isDoneText() = if (done) "done" else "not done"

    var done = false

}
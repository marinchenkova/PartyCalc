package name.marinchenko.partycalc.core.item

data class Result(
        val who: Payer,
        val toWhom: Payer,
        val sumString: String
) {

    var done = false

}
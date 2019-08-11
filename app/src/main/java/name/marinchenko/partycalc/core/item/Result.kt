package name.marinchenko.partycalc.core.item

data class Result(
        private val who: Payer,
        private val toWhom: Payer,
        val sum: Int
) {
    private var done = false

    fun done() { done = true }
    fun isDone() = done

    fun whoName() = who.title
    fun toWhomName() = toWhom.title
}
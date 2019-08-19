package name.marinchenko.partycalc.core.item

import name.marinchenko.partycalc.core.PartyCalc

abstract class Item(
        override val id: Long,
        val hintTitle: String,
        val hintSum: String,
        override val num: Int
): NumItem, Textable {

    var title = ""
    var sumString = ""


    fun getAvailableTitle() = if (title.isEmpty()) hintTitle else title

    fun sum(): Double {
        return try {
            PartyCalc.parseSumString(sumString)
        } catch (e: Exception) {
            0.toDouble()
        }
    }

    override fun toString() = "${javaClass.simpleName}(str=$id, title=$title, sum=${sum()}, hintTitle=$hintTitle)"

    override fun hashCode() = id.toInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Item
        if (id != other.id) return false
        return true
    }

}
package name.marinchenko.partycalc.core.item

import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.formatDouble

abstract class Item(
        override val id: Long,
        var hintTitle: String,
        var hintSum: String,
        override val num: Int
): NumItem, Textable {

    var title = ""
    var sumString = ""


    fun getAvailableTitle() = if (title.isEmpty()) hintTitle else title

    fun sum() = PartyCalc.parseSumString(sumString)

    fun availableSum() = formatDouble(PartyCalc.parseSumString(
            if (sumString.isEmpty()) hintSum else sumString
    ))

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
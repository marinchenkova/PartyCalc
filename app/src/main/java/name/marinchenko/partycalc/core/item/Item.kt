package name.marinchenko.partycalc.core.item

import android.graphics.Color

abstract class Item(
        val id: Long,
        val hintTitle: String,
        val hintSum: String,
        val num: Int
) {

    var title = ""
    var sumString = ""

    fun sum(): Int {
        return try {
            sumString.toInt()
        } catch (e: Exception) {
            0
        }
    }

    override fun toString() = "${javaClass.simpleName}(id=$id, title=$title, sum=${sum()}, hintTitle=$hintTitle)"

    override fun hashCode() = id.toInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Item
        if (id != other.id) return false
        return true
    }

}
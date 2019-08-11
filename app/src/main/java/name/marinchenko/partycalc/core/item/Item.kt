package name.marinchenko.partycalc.core.item

import name.marinchenko.partycalc.core.id.Id

abstract class Item<I: Id<*>>(val id: I) {

    var title = ""
    var sum = 0

    override fun toString() = "${javaClass.simpleName}(id=$id, title=$title, sum=$sum)"

    override fun hashCode() = id.toInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Item<*>
        if (id != other.id) return false
        return true
    }

}
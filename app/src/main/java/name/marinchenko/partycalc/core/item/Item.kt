package name.marinchenko.partycalc.core.item

abstract class Item(val id: Long) {

    var title = ""
    var sum = 0

    fun sumString() = "$sum"

    override fun toString() = "${javaClass.simpleName}(id=$id, title=$title, sum=$sum)"

    override fun hashCode() = id.toInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Item
        if (id != other.id) return false
        return true
    }

}
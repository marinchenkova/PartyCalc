package name.marinchenko.partycalc.core.id

abstract class Id<T>(protected val id: T) : Comparable<Id<T>> {

    abstract fun toInt(): Int

    fun id() = id

    open fun byte(): Byte = toInt().toByte()

    override fun compareTo(other: Id<T>) = toInt() - other.toInt()
}
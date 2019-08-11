package name.marinchenko.partycalc.core.id

abstract class Id<T>(protected val id: T) : Comparable<Id<T>> {

    fun id() = id

    open fun toByte(): Byte = toInt().toByte()

    abstract fun toLong(): Long
    abstract fun toInt(): Int

    override fun compareTo(other: Id<T>) = toInt() - other.toInt()
}
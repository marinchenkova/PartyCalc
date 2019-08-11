package name.marinchenko.partycalc.core.id

class ProductId(id: Int): Id<Int>(id) {

    override fun toLong() = id.toLong()
    override fun toInt() = id

}
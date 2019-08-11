package name.marinchenko.partycalc.core.id

class ProductId(id: Int): Id<Int>(id) {

    override fun toInt() = id

}
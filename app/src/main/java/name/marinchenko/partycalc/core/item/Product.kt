package name.marinchenko.partycalc.core.item

class Product(
        id: Long,
        hintTitle: String,
        hintSum: String,
        val color: Int
): Item(
        id,
        hintTitle,
        hintSum
)

package name.marinchenko.partycalc.core.item

class Payer(
        id: Long,
        hintTitle: String,
        hintSum: String,
        num: Int
): Item(
        id,
        hintTitle,
        hintSum,
        num
) {

    private val productIds = mutableSetOf<Long>()

    fun productSum(products: List<Product>) = products
                    .filter { productIds.contains(it.id) }
                    .sumBy { it.sum() }

    fun addProduct(productId: Long) {
        productIds.add(productId)
    }

    fun removeProduct(productId: Long) {
        productIds.remove(productId)
    }

    fun removeAllProducts() {
        productIds.clear()
    }

}
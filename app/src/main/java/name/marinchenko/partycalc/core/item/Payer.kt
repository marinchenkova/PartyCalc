package name.marinchenko.partycalc.core.item

class Payer(id: Long): Item(id) {

    private val productIds = mutableSetOf<Long>()

    fun productSum(products: List<Product>) = products
                    .filter { productIds.contains(it.id) }
                    .sumBy { it.sum }

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
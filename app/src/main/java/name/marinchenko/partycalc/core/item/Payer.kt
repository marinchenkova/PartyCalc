package name.marinchenko.partycalc.core.item

import name.marinchenko.partycalc.core.id.PayerId
import name.marinchenko.partycalc.core.id.ProductId

class Payer(id: PayerId): Item<PayerId>(id) {

    private val productIds = mutableSetOf<ProductId>()

    fun productSum(products: List<Product>) = products.filter {
        productIds.contains(it.id)
    }.sumBy {
        it.sum
    }

    fun addProduct(productId: ProductId) {
        productIds.add(productId)
    }

    fun removeProduct(productId: ProductId) {
        productIds.remove(productId)
    }

    fun removeAllProducts() {
        productIds.clear()
    }

}
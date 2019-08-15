package name.marinchenko.partycalc.android.util.itemFactory

import android.content.Context
import name.marinchenko.partycalc.android.util.*
import name.marinchenko.partycalc.core.item.Product

class ProductFactory(ctx: Context): ItemFactory<Product>(ctx) {

    override fun nextItem(usedNums: Set<Int>, usedIds: Set<Long>): Product {
        val num = randomExcept(0, RANDOM_PRODUCTS_NUM - 1, usedNums)

        return Product(
                nextId(usedIds),
                ctx.getRandomProductTitle(num),
                getRandomSumString(),
                ctx.getRandomColorResId(num),
                num
        )
    }

}
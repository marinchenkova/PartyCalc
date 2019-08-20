package name.marinchenko.partycalc.android.util.item

import android.content.Context
import name.marinchenko.partycalc.android.storage.getShowTitleHints
import name.marinchenko.partycalc.android.util.RANDOM_PRODUCTS_NUM
import name.marinchenko.partycalc.android.util.getRandomColorResId
import name.marinchenko.partycalc.android.util.getRandomProductTitle
import name.marinchenko.partycalc.core.item.Product
import name.marinchenko.partycalc.core.randomExcept

class ProductFactory(ctx: Context): ItemFactory<Product>(ctx) {

    override fun nextItem(usedNums: Set<Int>, usedIds: Set<Long>, sum: String): Product {
        val num = randomExcept(0, RANDOM_PRODUCTS_NUM - 1, usedNums)

        return Product(
                nextId(usedIds),
                if (ctx.getShowTitleHints()) ctx.getRandomProductTitle(num) else "",
                "0",
                ctx.getRandomColorResId(num),
                num
        ).also { it.sumString = sum }
    }

}
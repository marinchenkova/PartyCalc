package name.marinchenko.partycalc.android.util.itemFactory

import android.content.Context
import name.marinchenko.partycalc.android.util.*
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.toast

class ProductFactory(ctx: Context): ItemFactory(ctx) {

    override fun nextItem(used: Set<Int>): Product {
        val rand = randomExcept(0, RANDOM_PRODUCTS_NUM - 1, used)

        return Product(
                nextId(),
                ctx.getRandomProductTitle(rand),
                getRandomSumString(),
                ctx.getRandomColorResId(rand),
                rand
        )
    }

}
package name.marinchenko.partycalc.android.recycler.factory

import android.content.Context
import name.marinchenko.partycalc.android.util.getRandomColorResId
import name.marinchenko.partycalc.android.storage.getPreferredProductHint
import name.marinchenko.partycalc.android.storage.getPreferredSumHint
import name.marinchenko.partycalc.android.util.getNumByLanguage
import name.marinchenko.partycalc.core.item.Product
import name.marinchenko.partycalc.core.randomExcept


class ProductFactory(ctx: Context): ItemFactory<Product>(ctx) {

    override fun nextItem(present: List<Product>, sum: String): Product {
        val usedNums = present.map { it.num }.toHashSet()
        val usedIds = present.map { it.id }.toHashSet()
        val num = randomExcept(0, getNumByLanguage() - 1, usedNums)

        return Product(
                nextId(usedIds),
                ctx.getPreferredProductHint(num),
                ctx.getPreferredSumHint(),
                getRandomColorResId(ctx, num),
                num
        ).also { it.sumString = sum }
    }

}
package name.marinchenko.partycalc.android.util

import android.content.Context
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product


class ItemFactory(private val ctx: Context) {

    private var idCount = 0L
    private val productNums = mutableSetOf<Int>()
    private val payerNums = mutableSetOf<Int>()


    private fun nextId() = idCount++

    fun removedProduct(num: Int) {
        productNums.remove(num)
    }

    fun removedPayer(num: Int) {
        payerNums.remove(num)
    }

    fun nextProduct(): Product {
        val rand = randomExcept(0, RANDOM_PRODUCTS_NUM - 1, productNums)
        if (rand >= 0) productNums.add(rand)

        return Product(
                nextId(),
                ctx.getRandomProductTitle(rand),
                getRandomSumString(),
                ctx.getRandomColorResId(rand),
                rand
        )
    }

    fun nextPayer(): Payer {
        val rand = randomExcept(0, RANDOM_PAYERS_NUM - 1, payerNums)
        if (rand >= 0) payerNums.add(rand)

        return Payer(
                nextId(),
                ctx.getRandomPayerTitle(rand),
                getRandomSumString(),
                rand
        )
    }

}
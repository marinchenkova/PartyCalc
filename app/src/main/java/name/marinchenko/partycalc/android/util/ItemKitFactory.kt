package name.marinchenko.partycalc.android.util

import android.content.Context


class ItemKitFactory(private val ctx: Context) {

    private var idCount = 0L
    private val productNums = mutableSetOf<Int>()
    private val payerNums = mutableSetOf<Int>()

    private fun nextId() = idCount++

    fun nextProductKit(): ItemDecoratorKit {
        val rand = randomExcept(0, RANDOM_TITLES_NUM - 1, productNums)
        productNums.add(rand)

        return ItemDecoratorKit(
                nextId(),
                ctx.getRandomProductTitle(rand),
                getRandomSumString(),
                ctx.getRandomColorResId(rand)
        )
    }

    fun nextPayerKit(): ItemDecoratorKit {
        val rand = randomExcept(0, RANDOM_TITLES_NUM - 1, payerNums)
        payerNums.add(rand)

        return ItemDecoratorKit(
                nextId(),
                ctx.getRandomPayerTitle(rand),
                getRandomSumString(),
                0
        )
    }

}
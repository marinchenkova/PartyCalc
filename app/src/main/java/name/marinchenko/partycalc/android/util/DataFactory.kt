package name.marinchenko.partycalc.android.util

import android.content.Context

private const val RANDOM_TITLES_NUM = 10

class DataFactory(private val ctx: Context) {

    private var idCount = 0L
    private val productNums = mutableSetOf<Int>()
    private val payerNums = mutableSetOf<Int>()


    fun nextId() = idCount++

    fun nextProductTitle(num: Int? = null): String {
        val rand = randomExcept(0, RANDOM_TITLES_NUM - 1, productNums)
        val title = ctx.getRandomProductTitle(num ?: rand)
        productNums.add(num ?: rand)
        return title
    }

    fun nextPayerTitle(num: Int? = null): String {
        val rand = randomExcept(0, RANDOM_TITLES_NUM - 1, payerNums)
        val title = ctx.getRandomPayerTitle(num ?: rand)
        payerNums.add(num ?: rand)
        return title
    }

}
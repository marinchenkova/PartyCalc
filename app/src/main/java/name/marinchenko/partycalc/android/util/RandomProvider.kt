package name.marinchenko.partycalc.android.util

import android.content.Context
import name.marinchenko.partycalc.R


fun randomInt(from: Int, to: Int) = (from..to).random()
fun randomLong(from: Long, to: Long) = (from..to).random()

fun Context.getRandomSumString(): String {
    val list = mutableListOf<String>()
    for (i in 1..randomInt(1, 3)) {
        list.add("${randomInt(1, 9) * 100}")
    }
    return list.joinToString("+") { it }
}

fun Context.getRandomProductTitle(): String = getString(when (randomInt(0, 9)) {
    0 -> R.string.product_random_0
    1 -> R.string.product_random_1
    2 -> R.string.product_random_2
    3 -> R.string.product_random_3
    4 -> R.string.product_random_4
    5 -> R.string.product_random_5
    6 -> R.string.product_random_6
    7 -> R.string.product_random_7
    8 -> R.string.product_random_8
    else -> R.string.product_random_9
})

fun Context.getRandomPayerTitle(): String = getString(when (randomInt(0, 9)) {
    0 -> R.string.payer_random_0
    1 -> R.string.payer_random_1
    2 -> R.string.payer_random_2
    3 -> R.string.payer_random_3
    4 -> R.string.payer_random_4
    5 -> R.string.payer_random_5
    6 -> R.string.payer_random_6
    7 -> R.string.payer_random_7
    8 -> R.string.payer_random_8
    else -> R.string.payer_random_9
})
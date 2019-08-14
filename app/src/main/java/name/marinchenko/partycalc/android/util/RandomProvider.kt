package name.marinchenko.partycalc.android.util

import android.content.Context
import name.marinchenko.partycalc.R


const val RANDOM_PRODUCTS_NUM = 10
const val RANDOM_PAYERS_NUM = 26
const val RANDOM_COLORS_NUM = 20


fun randomInt(from: Int, to: Int) = (from..to).random()

fun randomExcept(from: Int, to: Int, except: Set<Int>): Int {
    val list = (from..to).toList().minus(except)
    return if (list.isEmpty()) -1 else list[randomInt(0, list.size - 1)]
}

fun getRandomSumString(): String {
    val list = mutableListOf<String>()
    for (i in 1..randomInt(1, 3)) {
        list.add("${randomInt(1, 9) * 100}")
    }
    return list.joinToString("+") { it }
}

fun Context.getRandomColorResId(num: Int? = null) = getColor(
        when (num ?: randomInt(0, RANDOM_COLORS_NUM - 1)) {
            0 -> R.color.mat100_0
            1 -> R.color.mat100_1
            2 -> R.color.mat100_2
            3 -> R.color.mat100_3
            4 -> R.color.mat100_4
            5 -> R.color.mat100_5
            6 -> R.color.mat100_6
            7 -> R.color.mat100_7
            8 -> R.color.mat100_8
            9 -> R.color.mat100_9
            10 -> R.color.mat100_10
            11 -> R.color.mat100_11
            12 -> R.color.mat100_12
            13 -> R.color.mat100_13
            14 -> R.color.mat100_14
            15 -> R.color.mat100_15
            16 -> R.color.mat200_0
            17 -> R.color.mat200_1
            18 -> R.color.mat200_2
            else -> R.color.mat200_3
        }
)

fun Context.getRandomProductTitle(num: Int? = null): String = getString(
        when (num ?: randomInt(0, RANDOM_PRODUCTS_NUM - 1)) {
            0 -> R.string.product_random_0
            1 -> R.string.product_random_1
            2 -> R.string.product_random_2
            3 -> R.string.product_random_3
            4 -> R.string.product_random_4
            5 -> R.string.product_random_5
            6 -> R.string.product_random_6
            7 -> R.string.product_random_7
            8 -> R.string.product_random_8
            9 -> R.string.product_random_9
            else -> R.string.product_default
        }
)

fun Context.getRandomPayerTitle(num: Int? = null): String = getString(
        when (num ?: randomInt(0, RANDOM_PAYERS_NUM - 1)) {
            0 -> R.string.payer_random_0
            1 -> R.string.payer_random_1
            2 -> R.string.payer_random_2
            3 -> R.string.payer_random_3
            4 -> R.string.payer_random_4
            5 -> R.string.payer_random_5
            6 -> R.string.payer_random_6
            7 -> R.string.payer_random_7
            8 -> R.string.payer_random_8
            9 -> R.string.payer_random_9
            10 -> R.string.payer_random_10
            11 -> R.string.payer_random_11
            12 -> R.string.payer_random_12
            13 -> R.string.payer_random_13
            14 -> R.string.payer_random_14
            15 -> R.string.payer_random_15
            16 -> R.string.payer_random_16
            17 -> R.string.payer_random_17
            18 -> R.string.payer_random_18
            19 -> R.string.payer_random_19
            20 -> R.string.payer_random_20
            21 -> R.string.payer_random_21
            22 -> R.string.payer_random_22
            23 -> R.string.payer_random_23
            24 -> R.string.payer_random_24
            25 -> R.string.payer_random_25
            else -> R.string.payer_default
        }
)
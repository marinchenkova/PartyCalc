package name.marinchenko.partycalc.android.util

import android.content.Context
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.core.randomInt


const val RANDOM_PRODUCTS_NUM = 33
const val RANDOM_PAYERS_NUM = 33
const val RANDOM_COLORS_NUM = 33


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
            19 -> R.color.mat200_3
            20 -> R.color.mat200_4
            21 -> R.color.mat200_5
            22 -> R.color.mat200_6
            23 -> R.color.mat200_7
            24 -> R.color.mat200_8
            25 -> R.color.mat200_9
            26 -> R.color.mat200_10
            27 -> R.color.mat200_11
            28 -> R.color.mat200_12
            29 -> R.color.mat200_13
            30 -> R.color.mat200_14
            31 -> R.color.mat200_15
            32 -> R.color.mat300_0
            else -> R.color.mat300_1
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
            10 -> R.string.product_random_10
            11 -> R.string.product_random_11
            12 -> R.string.product_random_12
            13 -> R.string.product_random_13
            14 -> R.string.product_random_14
            15 -> R.string.product_random_15
            16 -> R.string.product_random_16
            17 -> R.string.product_random_17
            18 -> R.string.product_random_18
            19 -> R.string.product_random_19
            20 -> R.string.product_random_20
            21 -> R.string.product_random_21
            22 -> R.string.product_random_22
            23 -> R.string.product_random_23
            24 -> R.string.product_random_24
            25 -> R.string.product_random_25
            26 -> R.string.product_random_26
            27 -> R.string.product_random_27
            28 -> R.string.product_random_28
            29 -> R.string.product_random_29
            30 -> R.string.product_random_30
            31 -> R.string.product_random_31
            32 -> R.string.product_random_32
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
            26 -> R.string.payer_random_26
            27 -> R.string.payer_random_27
            28 -> R.string.payer_random_28
            29 -> R.string.payer_random_29
            30 -> R.string.payer_random_30
            31 -> R.string.payer_random_31
            32 -> R.string.payer_random_32
            else -> R.string.payer_default
        }
)
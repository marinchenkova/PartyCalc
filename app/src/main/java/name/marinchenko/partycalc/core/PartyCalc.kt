package name.marinchenko.partycalc.core

import com.fathzer.soft.javaluator.DoubleEvaluator
import name.marinchenko.partycalc.core.item.Item
import kotlin.random.Random

class PartyCalc {

    fun itemListSum(items: List<Item>): String {
        val sum = items.sumByDouble { it.sum() }
        return String.format("%.2f", sum)
    }

    companion object {

        @JvmStatic
        private val seps = arrayOf("+", "-", "*", "/")

        @JvmStatic
        fun parseSumString(sumString: String): Double {
            return DoubleEvaluator().evaluate(sumString)
        }

        @JvmStatic
        fun getRandomHintSum(): String {
            val list = mutableListOf<String>()
            for (i in 1..randomInt(1, 3)) {
                list.add("${randomInt(1, 9) * 100}")
            }
            return list.joinToString("+") {
                it
            }
        }

    }
}

fun randomInt(from: Int, to: Int) = (from..to).random()

fun randomExcept(used: Set<Long>): Long {
    val res = Random.nextLong()
    return if (used.contains(res)) randomExcept(used)
    else res
}

fun randomExcept(from: Int, to: Int, used: Set<Int>): Int {
    val list = (from..to).toList().minus(used)
    return if (list.isEmpty()) -1 else list[randomInt(0, list.size - 1)]
}
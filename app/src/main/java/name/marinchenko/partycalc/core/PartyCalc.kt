package name.marinchenko.partycalc.core

import com.fathzer.soft.javaluator.DoubleEvaluator
import name.marinchenko.partycalc.core.item.Item
import kotlin.random.Random

class PartyCalc {

    companion object {

        @JvmStatic
        fun itemListSum(items: List<Item>): Double {
            return items.sumByDouble { it.sum() }
        }

        @JvmStatic
        fun parseSumString(sumString: String): Double {
            return DoubleEvaluator().evaluate(sumString)
        }

        @JvmStatic
        fun getRandomHintSum(): String {
            val list = mutableListOf<String>()
            var sum = 0
            for (i in 1..randomInt(1, 2)) {
                val random = randomInt(1, 9) * 100
                sum += random
                list.add("$random")
            }

            val sumString = list.joinToString("+") { it }
            val braces = if (list.size > 1) "($sumString)" else sumString

            return when (randomInt(0, 9)) {
                in 0..4 -> sumString
                in 5..7 -> {
                    val minus = randomInt(0, (sum / 100) - 1) * 100
                    if (minus > 0) "$braces-$minus"
                    else sumString
                }
                else -> "$braces${if (eagle()) "*" else "/"}${randomInt(2, 9)}"
            }

        }

    }
}

fun eagle() = randomInt(0, 1) == 0

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
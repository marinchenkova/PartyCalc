package name.marinchenko.partycalc.core

import com.fathzer.soft.javaluator.DoubleEvaluator
import name.marinchenko.partycalc.core.item.*
import org.w3c.dom.Text
import java.lang.Exception
import kotlin.random.Random

class PartyCalc(
        private val products: List<Product>,
        private val payers: List<Payer>
) {

    fun calculate(): List<Result> {
        val prodSubs = productSubscribers()
        val prodAverages = productAverages(prodSubs)
        val payerDebts = payerDebt(prodAverages)

        val givers = mutableMapOf<Payer, Double>()
        val receivers = mutableMapOf<Payer, Double>()

        payerDebts.forEach {
            if (it.key.sum() < it.value) givers[it.key] = it.value - it.key.sum()
            else if (it.key.sum() > it.value) receivers[it.key] = it.key.sum() - it.value
        }

        val sides = makeSides(givers, receivers)

        val results = mutableListOf<Result>()
        for ((giver, receiversOf) in sides) {
            results.addAll(receiversOf.map { receiver ->
                Result(giver, receiver.key, receiver.value)
            })
        }

        return results
    }

    private fun productSubscribers(): Map<Product, Int> {
        val res = mutableMapOf<Product, Int>()
        products.forEach { res[it] = 0 }

        payers.forEach { payer ->
            payer.payerChecks.forEach { check ->
                if (check.isChecked) {
                    res[check.product] = res.getValue(check.product) + 1
                }
            }
        }

        return res
    }

    private fun productAverages(productSubs: Map<Product, Int>): Map<Product, Double> {
        val res = mutableMapOf<Product, Double>()
        productSubs.forEach { res[it.key] = it.key.sum() / it.value }

        return res
    }

    private fun payerDebt(productAverages: Map<Product, Double>): Map<Payer, Double> {
        val res = mutableMapOf<Payer, Double>()

        payers.forEach { res[it] = 0.0 }
        payers.forEach { payer ->
            payer.payerChecks.forEach { check ->
                if (check.isChecked)
                    res[payer] = res.getValue(payer) + productAverages.getValue(check.product)
            }
        }

        return res
    }

    private fun makeSides(
            givers: MutableMap<Payer, Double>,
            receivers: MutableMap<Payer, Double>
    ): Map<Payer, Map<Payer, Double>> {
        val sides = mutableMapOf<Payer, Map<Payer, Double>>()

        givers.forEach { giver ->
            var sumToGive = giver.value
            val receiverSums = mutableMapOf<Payer, Double>()
            val receiversToRemove = mutableListOf<Payer>()

            for ((receiver, sumToReceive) in receivers) {
                if (sumToGive >= sumToReceive) {
                    receiverSums[receiver] = sumToReceive
                    sumToGive -= sumToReceive
                    receiversToRemove.add(receiver)
                }
                else {
                    receiverSums[receiver] = sumToGive
                    receivers[receiver] = sumToReceive - sumToGive
                    break
                }
            }

            receiversToRemove.forEach { receivers.remove(it) }
            sides[giver.key] = receiverSums
        }

        return sides
    }

    class TextBuilder {

        private var stringBuilder = StringBuilder()

        fun build() = stringBuilder.toString()

        fun title(title: String, include: Boolean = true) = appendText("$title\n", include)

        fun products(products: List<Product>, include: Boolean = true): TextBuilder {
            val text = "${products.size} categories for " +
                        itemListSumString(products) + ":\n" +
                        itemsText(products) + "\n"
            return appendText(text, include)
        }

        fun payers(payers: List<Payer>, include: Boolean = true): TextBuilder {
            val text = "${payers.size} payers for " +
                    itemListSumString(payers) + ":\n" +
                    itemsText(payers) + "\n"
            return appendText(text, include)
        }

        fun results(results: List<Result>, include: Boolean = true): TextBuilder {
            val text = "Results:\n" + itemsText(results) + "\n"
            return appendText(text, include)
        }

        private fun appendText(text: String, include: Boolean = true): TextBuilder {
            if (include) stringBuilder.append(text)
            return this
        }

    }

    companion object {

        @JvmStatic
        fun calculateParty(products: List<Product>, payers: List<Payer>) =
                PartyCalc(products, payers).calculate()

        @JvmStatic
        fun itemListSum(items: List<Item>): Double {
            return items.sumByDouble { it.sum() }
        }

        @JvmStatic
        fun itemListSumString(items: List<Item>): String {
            return formatDouble(itemListSum(items))
        }

        @JvmStatic
        fun parseSumString(sumString: String): Double {
            return try {
                DoubleEvaluator().evaluate(sumString)
            } catch (e: Exception) {
                0.0
            }
        }

        @JvmStatic
        fun getRandomHintSum(): String {
            val list = mutableListOf<String>()
            var sum = 0
            for (i in 1..randomInt(1, 2)) {
                val random = randomInt(1, 9) * 10
                sum += random
                list.add("$random")
            }

            val sumString = list.joinToString("+") { it }
            val braces = if (list.size > 1) "($sumString)" else sumString

            return when (randomInt(0, 9)) {
                in 0..4 -> sumString
                in 5..7 -> {
                    val minus = randomInt(0, (sum / 10) - 1) * 10
                    if (minus > 0) "$braces-$minus"
                    else sumString
                }
                else -> "$braces${if (eagle()) "*" else "/"}${randomInt(2, 9)}"
            }
        }

        @JvmStatic
        fun itemsText(items: List<Textable>): String {
            return if (items.isEmpty()) "empty"
            else items.joinToString("\n") { it.toText() }
        }

        @JvmStatic
        fun resultsDone(results: List<Result>): String {
            return "${results.filter { it.isDone }.size}/${results.size}"
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

fun formatDouble(num: Double) = String.format("%.2f", num)
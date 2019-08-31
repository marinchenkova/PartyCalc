package name.marinchenko.partycalc.android.recycler.viewHolder

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.MainActivity
import name.marinchenko.partycalc.android.util.*
import name.marinchenko.partycalc.android.util.summary.Summary
import name.marinchenko.partycalc.android.util.summary.SummaryState
import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.formatDouble
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product
import name.marinchenko.partycalc.core.item.Result
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.toast
import kotlin.math.abs

class SummaryViewHolder(private val activity: MainActivity) {

    private val products = mutableListOf<Product>()
    private val payers = mutableListOf<Payer>()
    private var productSum = 0.0
    private var payerSum = 0.0

    private var background: Drawable? = null
    private var onSumEquality: ((results: List<Result>) -> Unit)? = null
    private var onLoad: (() -> Unit)? = null

    init {
        background = activity.no_results_layout?.background
    }

    fun onSumEqualityAction(action: (results: List<Result>) -> Unit): SummaryViewHolder {
        onSumEquality = action
        return this
    }

    fun onLoad(action: () -> Unit): SummaryViewHolder {
        onLoad = action
        return this
    }

    fun load(payers: List<Payer>, products: List<Product>) {
        payersUpdated(payers)
        productsUpdated(products)
        loadSummary()
        onLoad?.invoke()
    }

    fun update(payers: List<Payer>, products: List<Product>) {
        payersUpdated(payers)
        productsUpdated(products)
        sumEquality()
    }

    private fun productsUpdated(new: List<Product>) {
        products.clear()
        products.addAll(new)
        productSum = PartyCalc.itemListSum(products)
        bindProductSummary(Summary(products.size, productSum))
    }

    private fun payersUpdated(new: List<Payer>) {
        payers.clear()
        payers.addAll(new)
        payerSum = PartyCalc.itemListSum(payers)
        bindPayerSummary(Summary(payers.size, payerSum))
    }

    private fun bindProductSummary(summary: Summary) {
        activity.result_products?.text = spanSummary(
                summary.size.toString(),
                activity.getProductCaseByNum(summary.size),
                formatDouble(summary.sum),
                getColor(activity, R.color.colorPrimary)
        )
    }

    private fun bindPayerSummary(summary: Summary) {
        activity.result_payers?.text = spanSummary(
                summary.size.toString(),
                activity.getPayerCaseByNum(summary.size),
                formatDouble(summary.sum),
                getColor(activity, R.color.colorPrimary)
        )
    }

    private fun sumDiff(): String {
        val diff = productSum - payerSum
        val sign = if (diff > 0) "+" else ""
        return "$sign${formatDouble(diff)}"
    }

    private fun sumEquality() {
        onSumEquality?.invoke(loadSummary())
    }

    private fun loadSummary(): List<Result> {
        val state = SummaryState(
                payers.isEmpty() || products.isEmpty(),
                abs(productSum - payerSum) <= 0
        ).state

        val results = when (state) {
            SummaryState.State.SumsAreNotEqual,
            SummaryState.State.NothingToCalculate -> emptyList()
            SummaryState.State.Ok -> calculate(products, payers)
        }

        initNoResults(state, results.isEmpty())

        return results
    }

    private fun calculate(products: List<Product>, payers: List<Payer>): List<Result>
            = PartyCalc.calculateParty(products, payers)
            .filter { it.sum > 0 }

    private fun initNoResults(state: SummaryState.State, resultsAreEmpty: Boolean) {
        when (state) {
            SummaryState.State.Ok -> {
                activity.no_results?.text = activity.getString(R.string.no_debts)
                activity.no_results?.textSize = 18f
                activity.no_results_layout?.background = background
            }

            SummaryState.State.SumsAreNotEqual -> {
                activity.no_results?.text = spanDiff(
                        activity.getString(R.string.results_payers_alert),
                        sumDiff(),
                        getColor(activity, R.color.colorPrimary)
                )
                activity.no_results?.textSize = 14f
                activity.no_results_layout?.backgroundColor = getColor(activity, R.color.colorRed_alert)
            }

            SummaryState.State.NothingToCalculate -> {
                activity.no_results?.text = activity.getString(R.string.nothing_to_calculate)
                activity.no_results?.textSize = 18f
                activity.no_results_layout?.background = background
            }
        }

        activity.no_results_layout?.setVisible(resultsAreEmpty)
    }

}
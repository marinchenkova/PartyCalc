package name.marinchenko.partycalc.android.viewHolder

import android.graphics.drawable.Drawable
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.MainActivity
import name.marinchenko.partycalc.android.storage.getIgnoreCentsTo
import name.marinchenko.partycalc.android.util.getStringByNum
import name.marinchenko.partycalc.android.util.item.Summary
import name.marinchenko.partycalc.android.util.item.SummaryState
import name.marinchenko.partycalc.android.util.setVisible
import name.marinchenko.partycalc.android.util.spanDiff
import name.marinchenko.partycalc.android.util.spanSummary
import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.formatDouble
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product
import name.marinchenko.partycalc.core.item.Result
import org.jetbrains.anko.backgroundColor

class SummaryViewHolder(private val activity: MainActivity) {

    private val products = mutableListOf<Product>()
    private var payers = mutableListOf<Payer>()
    private var productSum = 0.0
    private var payerSum = 0.0

    private var background: Drawable? = null
    private var onSumEquality: ((results: List<Result>) -> Unit)? = null

    init {
        background = activity.no_results_layout?.background
    }

    fun onSumEqualityAction(action: (results: List<Result>) -> Unit): SummaryViewHolder {
        onSumEquality = action
        return this
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
        activity.result_products?.text = initSummary(
                summary,
                activity.getString(R.string.result_1_product),
                activity.getString(R.string.result_products),
                activity.getColor(R.color.colorPrimary)
        )
    }

    private fun bindPayerSummary(summary: Summary) {
        activity.result_payers?.text = initSummary(
                summary,
                activity.getString(R.string.result_1_payer),
                activity.getString(R.string.result_payers),
                activity.getColor(R.color.colorPrimary)
        )
    }

    private fun sumDiff(): String {
        val diff = productSum - payerSum
        val sign = if (diff > 0) "+" else ""
        return "$sign${formatDouble(diff)}"
    }

    private fun sumEquality() {
        val state = SummaryState(
                payers.isEmpty() || products.isEmpty(),
                Math.abs(productSum - payerSum) <= activity.getIgnoreCentsTo()
        ).state

        val results = when (state) {
            SummaryState.State.SumsAreNotEqual,
            SummaryState.State.NothingToCalculate -> emptyList()
            SummaryState.State.Ok -> calculate()
        }

        initNoResults(state, results.isEmpty())

        onSumEquality?.invoke(results)
    }

    private fun calculate(): List<Result> = PartyCalc.calculateParty(products, payers)
            .filter { it.sum > activity.getIgnoreCentsTo() }

    private fun initSummary(
            summary: Summary,
            singular: String,
            plural: String,
            color: Int) = spanSummary(
            summary.size.toString(),
            getStringByNum(
                    summary.size,
                    singular,
                    plural
            ),
            formatDouble(summary.sum),
            color
    )

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
                        activity.getColor(R.color.colorPrimary)
                )
                activity.no_results?.textSize = 14f
                activity.no_results_layout?.backgroundColor = activity.getColor(R.color.colorRed_alert)
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
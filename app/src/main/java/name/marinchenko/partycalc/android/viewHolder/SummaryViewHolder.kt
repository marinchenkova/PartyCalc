package name.marinchenko.partycalc.android.viewHolder

import android.graphics.drawable.Drawable
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.MainActivity
import name.marinchenko.partycalc.android.util.getStringByNum
import name.marinchenko.partycalc.android.util.item.Summary
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
    private var onAddDiffPayer: ((sum: String) -> Unit)? = null


    init {
        background = activity.result_payers_layout?.background
        activity.result_payers_layout?.setOnClickListener {
            onAddDiffPayer?.invoke(formatDouble(productSum - payerSum))
        }
    }

    fun onSumEqualityAction(action: (results: List<Result>) -> Unit): SummaryViewHolder {
        onSumEquality = action
        return this
    }

    fun onAddDiffPayer(action: (sum: String) -> Unit): SummaryViewHolder {
        onAddDiffPayer = action
        return this
    }

    fun productsUpdated(new: List<Product>) {
        products.clear()
        products.addAll(new)
        productSum = PartyCalc.itemListSum(products)
        bindProductSummary(Summary(products.size, productSum))
        sumEquality()
    }

    fun payersUpdated(new: List<Payer>) {
        payers.clear()
        payers.addAll(new)
        payerSum = PartyCalc.itemListSum(payers)
        bindPayerSummary(Summary(payers.size, payerSum))
        sumEquality()
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
        val equal = formatDouble(productSum) == formatDouble(payerSum)
        activity.result_payers_alert?.text = spanDiff(
                activity.getString(R.string.results_payers_alert),
                sumDiff(),
                activity.getColor(R.color.colorPrimary)
        )

        activity.result_payers_alert?.setVisible(!equal)

        if (!equal) {
            activity.result_payers_layout?.backgroundColor = activity.getColor(R.color.colorRed_alert)
            onSumEquality?.invoke(emptyList())
            activity.no_results?.setVisible(true)
        }
        else {
            activity.result_payers_layout?.background = background
            val results = PartyCalc.calculateParty(products, payers)
            onSumEquality?.invoke(results)
            activity.no_results?.setVisible(results.isEmpty())
        }
    }

    private fun initSummary(
            summary: Summary,
            singular: String,
            plural: String,
            color: Int
    ) = spanSummary(
            summary.size.toString(),
            getStringByNum(
                    summary.size,
                    singular,
                    plural
            ),
            formatDouble(summary.sum),
            color
    )

}
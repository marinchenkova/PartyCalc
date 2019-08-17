package name.marinchenko.partycalc.android.viewHolder

import android.graphics.drawable.Drawable
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.MainActivity
import name.marinchenko.partycalc.android.util.getStringByNum
import name.marinchenko.partycalc.android.util.item.Summary
import name.marinchenko.partycalc.android.util.setVisibility
import name.marinchenko.partycalc.android.util.spanSummary
import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product
import org.jetbrains.anko.backgroundColor

class SummaryViewHolder(private val activity: MainActivity) {

    private var productSum = 0.0
    private var payerSum = 0.0
    private var background: Drawable? = null


    init {
        background = activity.result_payers_layout?.background
        productsUpdated(emptyList())
        payersUpdated(emptyList())
    }

    fun productsUpdated(new: List<Product>) {
        productSum = PartyCalc.itemListSum(new)
        val summary = Summary(new.size, productSum)
        bindProductSummary(summary)
        sumEquality()
    }

    fun payersUpdated(new: List<Payer>) {
        payerSum = PartyCalc.itemListSum(new)
        val summary = Summary(new.size, payerSum)
        bindPayerSummary(summary)
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

    private fun sumEquality() {
        val equal = String.format("%.2f", productSum) == String.format("%.2f", payerSum)
        activity.result_payers_alert?.setVisibility(!equal)
        if (!equal)
            activity.result_payers_layout?.backgroundColor = activity.getColor(R.color.colorRed_alert)
        else activity.result_payers_layout?.background = background
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
            String.format("%.2f", summary.sum),
            color
    )

}
package name.marinchenko.partycalc.android.util

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.VibrationEffect
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.EditText
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.core.formatDouble
import org.jetbrains.anko.vibrator
import java.lang.Exception
import java.util.*


const val PAYER_ITEM_MARGINS = 32
const val BORDER_WIDTH_DP = 2


fun View.isVisible() = visibility == View.VISIBLE

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.highlight(selected: Boolean) {
    //alpha = if (selected) 0.5f else 1f
}

fun EditText.afterInput(action: (text: String) -> Unit) {
    setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) action(this.text.toString()) }
}

fun EditText.afterTextChanged(action: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) = action(p0.toString())
    })
}

fun View.setBorder(color: Int, width: Int) {
    val border = GradientDrawable()
    border.setColor(Color.TRANSPARENT)
    border.setStroke(width, color)
    background = border
}

fun Context.dpToPixels(dp: Int) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
)

fun spanSummary(start: String, middle: String, end: String, color: Int): SpannableStringBuilder {
    val startBuilder = SpannableStringBuilder(start)
            .span(StyleSpan(Typeface.BOLD))
            .span(ForegroundColorSpan(color))

    val endBuilder = SpannableStringBuilder(end)
            .span(StyleSpan(Typeface.BOLD))
            .span(ForegroundColorSpan(color))

    return startBuilder.append(" ").append(middle).append(" ").append(endBuilder)
}

fun spanDiff(start: String, end: String, color: Int): SpannableStringBuilder {
    val endBuilder = SpannableStringBuilder(end)
            .span(StyleSpan(Typeface.BOLD))
            .span(ForegroundColorSpan(color))

    return SpannableStringBuilder(start).append(": ").append(endBuilder)
}

fun SpannableStringBuilder.span(what: Any): SpannableStringBuilder {
    setSpan(what, 0, length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

fun Context.getProductCaseByNum(num: Int) = getCaseByNum(
        num,
        getString(R.string.result_1_product),
        getString(R.string.result_2_3_4_products),
        getString(R.string.result_products)
)

fun Context.getPayerCaseByNum(num: Int) = getCaseByNum(
        num,
        getString(R.string.result_1_payer),
        getString(R.string.result_2_3_4_payers),
        getString(R.string.result_payers)
)

fun getCaseByNum(num: Int, case1: String, case234: String, caseElse: String): String {
    return when (num % 100) {
        1, 21, 31, 41, 51, 61, 71, 81, 91 -> case1
        2, 3, 4, 22, 23, 24, 32, 33, 34,
        42, 43, 44, 52, 53, 54, 62, 63, 64,
        72, 73, 74, 82, 83, 84, 92, 93, 94 -> case234
        else -> caseElse
    }
}

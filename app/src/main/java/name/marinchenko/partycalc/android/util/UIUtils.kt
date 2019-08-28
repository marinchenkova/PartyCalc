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
import android.widget.EditText
import org.jetbrains.anko.vibrator
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

fun isSingular(num: Int) = num == 1

fun getStringByNum(num: Int, singular: String, plural: String): String {
    return if (isSingular(num)) singular else plural
}

fun List<Any>.swapItems(from: Int, to: Int) {
    if (from < to) for (i in from until to) {
        Collections.swap(this, i, i + 1)
    }
    else for (i in from downTo to + 1) {
        Collections.swap(this, i, i - 1)
    }
}
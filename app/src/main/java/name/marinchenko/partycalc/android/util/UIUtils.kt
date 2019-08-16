package name.marinchenko.partycalc.android.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.VibrationEffect
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.vibrator


const val PAYER_ITEM_MARGINS = 32
const val BORDER_WIDTH_DP = 2


fun Context.makeVibration(ms: Long, amplitude: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(ms, amplitude))
    }
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
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

package name.marinchenko.partycalc.android.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.VibrationEffect
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import name.marinchenko.partycalc.android.adapter.PARENT_MARGIN
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.vibrator

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

fun View.setBorderOfColor(color: Int, width: Int) {
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

package name.marinchenko.partycalc.android.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.product_item.view.*
import org.jetbrains.anko.vibrator
import java.util.*

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
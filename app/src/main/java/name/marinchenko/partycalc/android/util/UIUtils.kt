package name.marinchenko.partycalc.android.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import org.jetbrains.anko.vibrator
import java.util.*

fun Context.makeVibration(ms: Long, amplitude: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(ms, amplitude))
    }
}

fun <T> swapItems(list: List<T>, from: Int, to: Int): List<T> {
    if (from < to) {
        for (i in from until to) Collections.swap(list, i, i + 1)
    }
    else {
        for (i in from downTo to + 1) {
            Collections.swap(list, i, i - 1)
        }
    }
    return list
}
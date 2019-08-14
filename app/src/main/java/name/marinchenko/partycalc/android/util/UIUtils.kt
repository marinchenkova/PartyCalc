package name.marinchenko.partycalc.android.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import org.jetbrains.anko.vibrator

fun Context.makeVibration(ms: Long, amplitude: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(ms, amplitude))
    }
}
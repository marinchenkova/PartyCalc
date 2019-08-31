package name.marinchenko.partycalc.android.util

import java.text.SimpleDateFormat
import java.util.*


private const val PATTERN_FULL = "dd.MM.yyyy HH:mm:ss"
private const val PATTERN_DM = "dd.MM"


fun now() = Calendar.getInstance().time

fun Date.formatFull() = format(PATTERN_FULL)

fun Date.formatDayMonth() = format(PATTERN_DM)

private fun Date.format(pattern: String): String {
    return SimpleDateFormat(pattern).format(this)
}
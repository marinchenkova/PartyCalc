package name.marinchenko.partycalc.android.util

import java.text.SimpleDateFormat
import java.util.*


private const val PATTERN_FULL = "dd.MM.yyyy HH:mm:ss"
private const val PATTERN_DMY = "dd.MM.yyyy"


fun now() = Calendar.getInstance().time

fun Date.formatFull() = format(PATTERN_FULL)

fun Date.formatDayMonthYear() = format(PATTERN_DMY)

private fun Date.format(pattern: String): String {
    return SimpleDateFormat(pattern).format(this)
}
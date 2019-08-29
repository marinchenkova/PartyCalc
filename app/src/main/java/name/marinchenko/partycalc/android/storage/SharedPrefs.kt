package name.marinchenko.partycalc.android.storage

import android.content.Context
import androidx.preference.PreferenceManager
import name.marinchenko.partycalc.android.util.getRandomPayerTitle
import name.marinchenko.partycalc.android.util.getRandomProductTitle
import name.marinchenko.partycalc.core.PartyCalc


private const val IGNORE_CENTS_TO_DEFAULT = "0"


private fun Context.prefs() = PreferenceManager.getDefaultSharedPreferences(this)


fun Context.getShareIncludeProducts() = prefs().getBoolean(Path.PREFS_SHARE_INCLUDE_PRODUCTS, false)
fun Context.getShareIncludePayers() = prefs().getBoolean(Path.PREFS_SHARE_INCLUDE_PAYRES, false)

fun Context.getPayerCheckDefaultState() = prefs().getBoolean(Path.PREFS_PAYER_CHECK_DEFAULT_STATE, true)

fun Context.getShowTitleHints() = prefs().getBoolean(Path.PREFS_SHOW_TITLE_HINTS, true)
fun Context.getPreferredProductHint(num: Int) = if (getShowTitleHints()) getRandomProductTitle(num) else ""
fun Context.getPreferredPayerHint(num: Int) = if (getShowTitleHints()) getRandomPayerTitle(num) else ""

fun Context.getShowSumHints() = prefs().getBoolean(Path.PREFS_SHOW_SUM_HINTS, true)
fun Context.getPreferredSumHint() = if (getShowSumHints()) PartyCalc.getRandomHintSum() else ""


object Path {
    const val PREFS_SHARE_INCLUDE_PRODUCTS = "share_include_products"
    const val PREFS_SHARE_INCLUDE_PAYRES = "share_include_payers"

    const val PREFS_PAYER_CHECK_DEFAULT_STATE = "payer_check_default_state"
    const val PREFS_IGNORE_CENTS_TO = "ignore_cents_to"

    const val PREFS_SHOW_TITLE_HINTS = "show_title_hints"
    const val PREFS_SHOW_SUM_HINTS = "show_sum_hints"
}

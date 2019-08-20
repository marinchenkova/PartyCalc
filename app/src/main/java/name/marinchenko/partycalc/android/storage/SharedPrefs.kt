package name.marinchenko.partycalc.android.storage

import android.content.Context


private const val IGNORE_CENTS_TO_DEFAULT = 0.00f


private fun Context.prefs() = getSharedPreferences(Path.PREFS_FILENAME, 0)


fun Context.getShareIncludeProducts() = prefs().getBoolean(Path.PREFS_SHARE_INCLUDE_PRODUCTS, true)
fun Context.setShareIncludeProducts(value: Boolean) = prefs().edit().putBoolean(Path.PREFS_SHARE_INCLUDE_PRODUCTS, value)

fun Context.getShareIncludePayers() = prefs().getBoolean(Path.PREFS_SHARE_INCLUDE_PAYRES, true)
fun Context.setShareIncludePayers(value: Boolean) = prefs().edit().putBoolean(Path.PREFS_SHARE_INCLUDE_PAYRES, value)

fun Context.getShareIncludeResults() = prefs().getBoolean(Path.PREFS_SHARE_INCLUDE_RESULTS, true)
fun Context.setShareIncludeResults(value: Boolean) = prefs().edit().putBoolean(Path.PREFS_SHARE_INCLUDE_RESULTS, value)


fun Context.getPayerCheckDefaultState() = prefs().getBoolean(Path.PREFS_PAYER_CHECK_DEFAULT_STATE, true)
fun Context.setPayerCheckDefaultState(value: Boolean) = prefs().edit().putBoolean(Path.PREFS_PAYER_CHECK_DEFAULT_STATE, value)

fun Context.getIgnoreCentsTo() = prefs().getFloat(Path.PREFS_IGNORE_CENTS_TO, IGNORE_CENTS_TO_DEFAULT).toDouble()
fun Context.setIgnoreCentsTo(value: Double) = prefs().edit().putFloat(Path.PREFS_IGNORE_CENTS_TO, value.toFloat())


fun Context.getShowTitleHints() = prefs().getBoolean(Path.PREFS_SHOW_TITLE_HINTS, true)
fun Context.setShowTitleHints(value: Boolean) = prefs().edit().putBoolean(Path.PREFS_SHOW_TITLE_HINTS, value)


object Path {
    const val PREFS_FILENAME = "name.marinchenko.partycalc.prefs"

    const val PREFS_SHARE_INCLUDE_PRODUCTS = "share_include_products"
    const val PREFS_SHARE_INCLUDE_PAYRES = "share_include_payers"
    const val PREFS_SHARE_INCLUDE_RESULTS = "share_include_results"

    const val PREFS_PAYER_CHECK_DEFAULT_STATE = "payer_check_default_state"

    const val PREFS_IGNORE_CENTS_TO = "ignore_cents_to"

    const val PREFS_SHOW_TITLE_HINTS = "show_title_hints"
    const val PREFS_SHOW_SUM_HINTS = "show_sum_hints"

}

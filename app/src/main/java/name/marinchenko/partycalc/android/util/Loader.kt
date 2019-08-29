package name.marinchenko.partycalc.android.util

class Loader(private val until: Int, private val onLoaded: () -> Unit) {

    private var count = 0

    fun loaded() {
        count++
        if (count >= until) onLoaded()
    }

}
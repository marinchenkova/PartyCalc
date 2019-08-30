package name.marinchenko.partycalc.android.util

class Loader (
        private val waitFor: Int,
        private val once: Boolean,
        vararg list: Any,
        private val onLoaded: () -> Unit
) {

    private val group = list.associate { it to false }.toMutableMap()
    private var wasLoaded = false

    fun loaded(obj: Any, ok: Boolean = true) {
        if (once && wasLoaded) return
        if (!group.contains(obj)) return

        group[obj] = ok

        val loadedNum = group.filter { it.value }.size
        if (loadedNum == waitFor) {
            wasLoaded = true
            onLoaded()
        }
    }

}
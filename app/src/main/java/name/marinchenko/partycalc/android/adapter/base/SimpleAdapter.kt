package name.marinchenko.partycalc.android.adapter.base

interface SimpleAdapter<I: Any> {

    fun addItem(item: I)

    fun removeItem(position: Int?)

}
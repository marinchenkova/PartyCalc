package name.marinchenko.partycalc.android.adapter

interface SimpleItemAdapter<I: Any> {

    fun addItem(item: I)

    fun removeItem(position: Int?)

}
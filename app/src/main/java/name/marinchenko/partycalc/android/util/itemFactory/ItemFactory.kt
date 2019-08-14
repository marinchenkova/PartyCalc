package name.marinchenko.partycalc.android.util.itemFactory

import android.content.Context
import name.marinchenko.partycalc.android.util.*
import name.marinchenko.partycalc.core.item.Item
import name.marinchenko.partycalc.core.item.Payer


abstract class ItemFactory(protected val ctx: Context) {

    private var idCount = 0L

    protected fun nextId() = idCount++

    abstract fun nextItem(used: Set<Int>): Item

}
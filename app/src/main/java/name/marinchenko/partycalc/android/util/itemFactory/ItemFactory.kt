package name.marinchenko.partycalc.android.util.itemFactory

import android.content.Context
import name.marinchenko.partycalc.android.util.*
import name.marinchenko.partycalc.core.item.Item
import name.marinchenko.partycalc.core.item.Payer


abstract class ItemFactory<I: Any>(protected val ctx: Context) {

    protected fun nextId(usedIds: Set<Long>) = randomExcept(usedIds)

    abstract fun nextItem(usedNums: Set<Int>, usedIds: Set<Long>): I

}
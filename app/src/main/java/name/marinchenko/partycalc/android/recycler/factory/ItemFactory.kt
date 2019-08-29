package name.marinchenko.partycalc.android.recycler.factory

import android.content.Context
import name.marinchenko.partycalc.core.randomExcept


abstract class ItemFactory<I: Any>(protected val ctx: Context) {

    protected fun nextId(usedIds: Set<Long>) = randomExcept(usedIds)

    abstract fun nextItem(present: List<I>, sum: String = ""): I

}
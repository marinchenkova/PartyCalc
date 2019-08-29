package name.marinchenko.partycalc.android.recycler.factory

import android.content.Context
import name.marinchenko.partycalc.android.util.RANDOM_PAYERS_NUM
import name.marinchenko.partycalc.android.storage.getPreferredPayerHint
import name.marinchenko.partycalc.android.storage.getPreferredSumHint
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.randomExcept

class PayerFactory(ctx: Context): ItemFactory<Payer>(ctx) {

    override fun nextItem(present: List<Payer>, sum: String): Payer {
        val usedNums = present.map { it.num }.toHashSet()
        val usedIds = present.map { it.id }.toHashSet()
        val num = randomExcept(0, RANDOM_PAYERS_NUM - 1, usedNums)

        return Payer(
                nextId(usedIds),
                ctx.getPreferredPayerHint(num),
                ctx.getPreferredSumHint(),
                num
        ).also { it.sumString = sum }
    }

}
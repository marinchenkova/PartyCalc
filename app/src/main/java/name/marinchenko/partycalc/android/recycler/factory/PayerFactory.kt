package name.marinchenko.partycalc.android.recycler.factory

import android.content.Context
import name.marinchenko.partycalc.android.storage.getShowTitleHints
import name.marinchenko.partycalc.android.util.RANDOM_PAYERS_NUM
import name.marinchenko.partycalc.android.util.getRandomPayerTitle
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.randomExcept

class PayerFactory(ctx: Context): ItemFactory<Payer>(ctx) {

    override fun nextItem(usedNums: Set<Int>, usedIds: Set<Long>, sum: String): Payer {
        val num = randomExcept(0, RANDOM_PAYERS_NUM - 1, usedNums)

        return Payer(
                nextId(usedIds),
                if (ctx.getShowTitleHints()) ctx.getRandomPayerTitle(num) else "",
                "0",
                num
        ).also { it.sumString = sum }
    }

}
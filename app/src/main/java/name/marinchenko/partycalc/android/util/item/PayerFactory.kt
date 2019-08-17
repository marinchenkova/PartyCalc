package name.marinchenko.partycalc.android.util.item

import android.content.Context
import name.marinchenko.partycalc.android.util.*
import name.marinchenko.partycalc.core.PartyCalc
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.randomExcept

class PayerFactory(ctx: Context): ItemFactory<Payer>(ctx) {

    override fun nextItem(usedNums: Set<Int>, usedIds: Set<Long>): Payer {
        val num = randomExcept(0, RANDOM_PAYERS_NUM - 1, usedNums)

        return Payer(
                nextId(usedIds),
                ctx.getRandomPayerTitle(num),
                PartyCalc.getRandomHintSum(),
                num
        )
    }

}
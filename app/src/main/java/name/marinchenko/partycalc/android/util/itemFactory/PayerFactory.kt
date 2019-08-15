package name.marinchenko.partycalc.android.util.itemFactory

import android.content.Context
import name.marinchenko.partycalc.android.util.*
import name.marinchenko.partycalc.core.item.Payer
import name.marinchenko.partycalc.core.item.Product

class PayerFactory(ctx: Context): ItemFactory<Payer>(ctx) {

    override fun nextItem(used: Set<Int>?): Payer {
        val rand = randomExcept(0, RANDOM_PAYERS_NUM - 1, used)

        return Payer(
                nextId(),
                ctx.getRandomPayerTitle(rand),
                getRandomSumString(),
                rand
        )
    }

}
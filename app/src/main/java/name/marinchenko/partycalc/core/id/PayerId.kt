package name.marinchenko.partycalc.core.id

import kotlin.experimental.inv

class PayerId(id: Int): Id<Int>(id) {

    override fun toInt() = id

    override fun byte() = super.byte().inv()

}
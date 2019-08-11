package name.marinchenko.partycalc.core.id

import kotlin.experimental.inv

class PayerId(id: Int): Id<Int>(id) {

    override fun toInt() = id
    override fun toLong() = id.toLong()

    override fun toByte() = super.toByte().inv()

}
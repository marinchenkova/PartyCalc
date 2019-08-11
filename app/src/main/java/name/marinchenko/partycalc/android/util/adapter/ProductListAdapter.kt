package name.marinchenko.partycalc.android.util.adapter

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.core.item.Item
import name.marinchenko.partycalc.core.item.Product

class ProductListAdapter(
        inflater: LayoutInflater,
        @LayoutRes itemLayout: Int
) : ListAdapter(inflater, itemLayout) {

    override fun fillView(item: Item<*>?, view: View?) {
        item as Product
        view?.findViewById<EditText>(R.id.item_title)?.setText(item.title)
        view?.findViewById<EditText>(R.id.item_sum)?.setText(item.sumString())
    }

}

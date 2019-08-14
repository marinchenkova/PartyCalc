package name.marinchenko.partycalc.android.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.listener.OnItemClickListener
import name.marinchenko.partycalc.android.util.viewHolder.ProductViewHolder

class ProductRecyclerAdapter(
        inflater: LayoutInflater,
        clickListener: OnItemClickListener
) : ItemRecyclerAdapter<ProductViewHolder>(
        inflater,
        R.layout.product_item,
        clickListener
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(inflater.inflate(
                itemLayout,
                parent,
                false
        ))
    }

}

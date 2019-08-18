package name.marinchenko.partycalc.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.viewHolder.ResultViewHolder
import name.marinchenko.partycalc.core.item.Result
import org.jetbrains.anko.layoutInflater

class ResultAdapter(private val ctx: Context) : RecyclerView.Adapter<ResultViewHolder>() {

    private val list = mutableListOf<Result>()


    fun getItems() = list

    fun updateList(new: List<Result>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(ctx, ctx.layoutInflater.inflate(
                R.layout.result_item,
                parent,
                false
        )).onDoneAction { isDone, position -> list[position].done = isDone }
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}
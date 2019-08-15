package name.marinchenko.partycalc.android.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.viewHolder.ResultViewHolder
import name.marinchenko.partycalc.core.item.Result

class ResultAdapter(private val inflater: LayoutInflater)
    : RecyclerView.Adapter<ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(inflater.inflate(
                R.layout.result_item,
                parent,
                false
        ))
    }

    private val list = mutableListOf<Result>()

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addResult(result: Result) {
        list.add(result)
        notifyDataSetChanged()
    }

    fun removeResult(result: Result) {
        list.remove(result)
        notifyDataSetChanged()
    }

    fun removeResult(position: Int?) {
        position ?: return
        list.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}
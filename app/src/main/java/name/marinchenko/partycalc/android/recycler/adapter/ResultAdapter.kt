package name.marinchenko.partycalc.android.recycler.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.recycler.adapter.base.BaseIdItemAdapter
import name.marinchenko.partycalc.android.recycler.viewHolder.ResultViewHolder
import name.marinchenko.partycalc.core.item.Result
import org.jetbrains.anko.layoutInflater

class ResultAdapter(private val ctx: Context) : RecyclerView.Adapter<ResultViewHolder>() {

    private val list = mutableListOf<Result>()
    private lateinit var onDone: (results: List<Result>) -> Unit
    private var onLoad: (() -> Unit)? = null


    fun onDoneAction(action: (results: List<Result>) -> Unit): ResultAdapter {
        onDone = action
        return this
    }

    fun onLoad(action: () -> Unit): ResultAdapter {
        onLoad = action
        return this
    }

    fun getItems() = list

    fun load(new: List<Result>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
        onLoad?.invoke()
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(ctx, ctx.layoutInflater.inflate(
                R.layout.result_item,
                parent,
                false
        )).onDoneAction { isDone, position ->
            list[position].isDone = isDone
            onDone(list)
        }
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}
package net.harimurti.tv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import net.harimurti.tv.BR
import net.harimurti.tv.R
import net.harimurti.tv.databinding.ItemCategoryBinding
import net.harimurti.tv.model.Category
import kotlin.math.round

class CategoryAdapter (cat: ArrayList<Category>?) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    lateinit var context: Context
    var categories: ArrayList<Category>? = cat

    class ViewHolder(var itemCatBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCatBinding.root) {
        fun bind(obj: Any?) {
            itemCatBinding.setVariable(BR.catModel,obj)
            itemCatBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: ItemCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),R.layout.item_category,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category: Category? = categories?.get(position)
        val chCount = category?.channels?.size ?: 0
        val spanCount = when {
            chCount > 30 -> chCount.toDouble().div(20).let { round(it).toInt() }
            chCount > 20 -> 2
            else -> 1
        }

        viewHolder.itemCatBinding.chAdapter = ChannelAdapter(category?.channels)
        viewHolder.itemCatBinding.rvChannels.layoutManager =
                StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)

        viewHolder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories?.size ?: 0
    }
}
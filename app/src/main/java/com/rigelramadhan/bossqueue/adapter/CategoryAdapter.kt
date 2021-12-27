package com.rigelramadhan.bossqueue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rigelramadhan.bossqueue.databinding.ItemCardFindplaceBinding
import com.rigelramadhan.bossqueue.model.Category

class CategoryAdapter(private val list: List<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCardFindplaceBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardFindplaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]

        holder.binding.tvPlaceName.text = category.name

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount() = list.size
}
package com.rigelramadhan.bossqueue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rigelramadhan.bossqueue.databinding.ItemCardFoodBinding
import com.rigelramadhan.bossqueue.model.Food

class FoodAdapter(private val list: List<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCardFoodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = list[position]
        holder.binding.tvFoodName.text = food.name

        holder.binding.cvFood.setOnClickListener {

        }
    }

    override fun getItemCount() = list.size

}
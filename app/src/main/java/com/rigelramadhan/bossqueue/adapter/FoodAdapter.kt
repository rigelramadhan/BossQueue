package com.rigelramadhan.bossqueue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.rigelramadhan.bossqueue.databinding.ItemCardFoodBinding
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.model.SampleData

class FoodAdapter(private val activity: AppCompatActivity, private val list: List<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCardFoodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = list[position]
        holder.binding.tvFoodName.text = food.name
        holder.binding.tvDetails.text = "Rp${food.price.toString()}"

        val storage = FirebaseStorage.getInstance()
        val gsRef = storage.getReferenceFromUrl(food.picture!!)

        gsRef.downloadUrl.addOnSuccessListener {
            if (!activity.isFinishing) {
                Glide.with(holder.itemView.context)
                    .load(it)
                    .apply(RequestOptions().override(150, 100))
                    .into(holder.binding.imgFood)
            }
        }

        holder.binding.cvFood.setOnClickListener {
            val addBtnVisibility = holder.binding.layoutAdd.visibility
            if (addBtnVisibility == View.INVISIBLE) {
                SampleData.bill -= food.price!!
                holder.binding.layoutAdd.visibility = View.VISIBLE
            } else {
                SampleData.bill += food.price!!
                holder.binding.layoutAdd.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount() = list.size

}
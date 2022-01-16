package com.rigelramadhan.bossqueue.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.rigelramadhan.bossqueue.databinding.ItemCardBasketBinding
import com.rigelramadhan.bossqueue.model.Basket
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.repository.BasketRepository
import com.rigelramadhan.bossqueue.repository.FoodRepository
import com.rigelramadhan.bossqueue.view.BasketActivity

class BasketAdapter(private val activity: AppCompatActivity, private val list: List<Basket>) : RecyclerView.Adapter<BasketAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCardBasketBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val basket = list[position]
        val food = FoodRepository.getFoodsByBasket(basket)
        val binding = holder.binding
        if (food != null) {
            binding.tvFoodName.text = food.name
        }

        if (food != null) {
            Log.d("BasketAdapter", "Food: ${food.name}, ${food.picture}")
        }

        val storage = FirebaseStorage.getInstance()
        val gsRef = storage.getReferenceFromUrl(food?.picture!!)

        gsRef.downloadUrl.addOnSuccessListener {
            if (!activity.isFinishing) {
                Glide.with(holder.itemView.context)
                    .load(it)
                    .apply(RequestOptions().override(150, 100))
                    .into(holder.binding.imgFood)
            }
        }

        binding.layoutRemove.setOnClickListener {
            val activity = this.activity as BasketActivity
            BasketRepository.deleteBasket(basket.basketId!!)
            this.notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = list.size
}
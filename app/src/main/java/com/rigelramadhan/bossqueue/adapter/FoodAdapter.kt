package com.rigelramadhan.bossqueue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.rigelramadhan.bossqueue.R
import com.rigelramadhan.bossqueue.databinding.ItemCardFoodBinding
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.repository.BasketRepository
import com.rigelramadhan.bossqueue.view.MenuActivity

class FoodAdapter(private val activity: MenuActivity, private val list: List<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCardFoodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = list[position]
        holder.binding.tvFoodName.text = food.name
        holder.binding.tvPrice.text = "Rp${food.price.toString()}"

        if (BasketRepository.checkBasketAvailability(food.id!!)) {
            buttonOff(holder)
        }

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
            when (holder.binding.layoutAdd.visibility) {
                View.VISIBLE -> {
                    activity.menuViewModel.createBasket(
                        FirebaseAuth.getInstance().uid!!,
                        food.id!!,
                        food.placeId!!
                    )
                    buttonOff(holder)
                }
                
                View.INVISIBLE -> {
                    BasketRepository.deleteBasketByFoodId(food.id!!)
                    buttonOn(holder, food)
                }
                
                else -> {}
            }
        }
    }

    override fun getItemCount() = list.size

    private fun buttonOn(holder: ViewHolder, food: Food) {
        holder.binding.layoutAdd.visibility = View.VISIBLE
        holder.binding.tvPrice.apply {
            text = "Rp${food.price.toString()}"
            setTextColor(activity.resources.getColor(R.color.white))
        }
    }

    private fun buttonOff(holder: ViewHolder) {
        holder.binding.layoutAdd.visibility = View.INVISIBLE
        holder.binding.tvPrice.apply {
            text = activity.getString(R.string.added_text)
            setTextColor(activity.resources.getColor(R.color.black))
        }
    }
}
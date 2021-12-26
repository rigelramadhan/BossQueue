package com.rigelramadhan.bossqueue.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.rigelramadhan.bossqueue.databinding.ItemCardPlaceBinding
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.view.MenuActivity

class PlaceAdapter(private val activity: AppCompatActivity, private val places: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCardPlaceBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]

        holder.binding.tvPlaceName.text = place.name
        holder.binding.tvPlaceLocation.text = place.location

        val storage = FirebaseStorage.getInstance()
        val gsRef = storage.getReferenceFromUrl(place.picture!!)

        gsRef.downloadUrl.addOnSuccessListener {
            if (!activity.isFinishing) {
                Glide.with(holder.itemView.context)
                    .load(it)
                    .apply(RequestOptions().override(150, 100))
                    .into(holder.binding.imgPlace)
            }
        }

        holder.binding.cvPlace.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            intent.putExtra(MenuActivity.EXTRA_PLACE_ID, place.id)

            activity.startActivity(intent)
        }
    }

    override fun getItemCount() = places.size

}
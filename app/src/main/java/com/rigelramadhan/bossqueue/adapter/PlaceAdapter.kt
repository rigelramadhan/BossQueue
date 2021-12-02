package com.rigelramadhan.bossqueue.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rigelramadhan.bossqueue.R
import com.rigelramadhan.bossqueue.databinding.ItemCardPlaceBinding
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.view.ui.menu.MenuActivity

class PlaceAdapter(private val context: Context, private val places: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCardPlaceBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]

        holder.binding.tvPlaceName.text = place.name
        holder.binding.tvPlaceLocation.text = place.location
        holder.binding.imgPlace.setImageDrawable(context.getDrawable(R.drawable.img_tes))

        holder.binding.cvPlace.setOnClickListener {
            val intent = Intent(context, MenuActivity::class.java)
            intent.putExtra(MenuActivity.EXTRA_PLACE_ID, place.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = places.size

}
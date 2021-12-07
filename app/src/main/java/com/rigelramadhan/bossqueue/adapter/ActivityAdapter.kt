package com.rigelramadhan.bossqueue.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rigelramadhan.bossqueue.databinding.ItemCardActivityBinding
import com.rigelramadhan.bossqueue.model.Activity
import com.rigelramadhan.bossqueue.model.SampleData

class ActivityAdapter(private val list: List<Activity>) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {
    // TODO: COMPLETE THE ADAPTERS
    class ViewHolder(var binding: ItemCardActivityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = list[position]

        val status = if (activity.paid) {
            holder.binding.layoutPay.visibility = View.INVISIBLE
            "Success"
        } else {
            "Pending"
        }

        holder.binding.tvStatus.text = status
    }

    override fun getItemCount() = list.size
}
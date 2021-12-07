package com.rigelramadhan.bossqueue.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.bossqueue.adapter.PlaceAdapter
import com.rigelramadhan.bossqueue.databinding.FragmentHomeBinding
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.data.observe(activity as AppCompatActivity, Observer {
            binding.rvHotnow.apply {
                adapter = PlaceAdapter(activity as AppCompatActivity, it)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            binding.rvNearyou.apply {
                val nearPlaces = mutableListOf<Place>()
                for (place in it) {
                    if (place.location == "Malang") {
                        nearPlaces.add(place)
                    }
                }
                adapter = PlaceAdapter(activity as AppCompatActivity, nearPlaces)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            binding.rvLastplace.apply {
                adapter = PlaceAdapter(activity as AppCompatActivity, it)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
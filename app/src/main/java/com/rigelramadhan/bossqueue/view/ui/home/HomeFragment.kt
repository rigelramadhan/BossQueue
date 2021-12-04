package com.rigelramadhan.bossqueue.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.bossqueue.adapter.CategoryAdapter
import com.rigelramadhan.bossqueue.adapter.PlaceAdapter
import com.rigelramadhan.bossqueue.controller.PlaceController
import com.rigelramadhan.bossqueue.databinding.FragmentHomeBinding
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.model.SampleData

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var placeController: PlaceController
    private lateinit var places: List<Place>
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        placeController = PlaceController(activity as AppCompatActivity)

        places = placeController.getPlaces()
        binding.rvFindplace.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(SampleData.categorySampleData)
        }

        placeController.configurePlacesRv(binding.rvHotnow, LinearLayoutManager.HORIZONTAL)
        placeController.configureNearPlacesRv(binding.rvNearyou, LinearLayoutManager.HORIZONTAL)
        placeController.configurePlacesRv(binding.rvLastplace, LinearLayoutManager.HORIZONTAL)

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
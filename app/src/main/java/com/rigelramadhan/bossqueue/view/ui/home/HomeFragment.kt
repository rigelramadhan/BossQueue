package com.rigelramadhan.bossqueue.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.bossqueue.adapter.CategoryAdapter
import com.rigelramadhan.bossqueue.adapter.PlaceAdapter
import com.rigelramadhan.bossqueue.databinding.FragmentHomeBinding
import com.rigelramadhan.bossqueue.model.SampleData

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFindplace.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(SampleData.categorySampleData)
        }

        binding.rvHotnow.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = PlaceAdapter(view.context, SampleData.placeSampleData)
        }

        binding.rvNearyou.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = PlaceAdapter(view.context, SampleData.placeSampleData)
        }

        binding.rvLastplace.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = PlaceAdapter(view.context, SampleData.placeSampleData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
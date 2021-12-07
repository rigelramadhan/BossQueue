package com.rigelramadhan.bossqueue.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rigelramadhan.bossqueue.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {
    // TODO: COMPLETE THE NOTIFICATION
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
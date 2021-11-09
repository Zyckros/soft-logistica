package com.example.softlogistica.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.BaseActivity
import com.example.softlogistica.databinding.FragmentChatBinding
import com.example.softlogistica.ui.home.HomeViewModel

class ChatFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })


        return root
    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).enableBottomNAv()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
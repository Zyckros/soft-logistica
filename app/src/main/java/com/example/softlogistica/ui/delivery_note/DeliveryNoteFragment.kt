package com.example.softlogistica.ui.delivery_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.BaseActivity
import com.example.softlogistica.R

class DeliveryNoteFragment : Fragment() {

    companion object {
        fun newInstance() = DeliveryNoteFragment()
    }

    private lateinit var viewModel: DeliveryNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.delivery_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DeliveryNoteViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).disableBottomNav()
    }

}
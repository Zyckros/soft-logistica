package com.example.softlogistica.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.BaseActivity
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.FragmentServicesBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class ServiceFragment : Fragment() {

    private lateinit var binding : FragmentServicesBinding
    private lateinit var serviceViewModel : ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = ApplicationDatabase.getInstance(application).productDatabaseDao
        val viewModelFactory = ServiceViewModelFactory(dataSource, application)

        binding = FragmentServicesBinding.inflate(inflater, container, false)
        serviceViewModel = ViewModelProvider(this, viewModelFactory).get(ServiceViewModel::class.java)
        binding.viewmodel = serviceViewModel
        binding.lifecycleOwner = this


        binding.setDatesButton.setOnClickListener {
            showDatePicker()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).disableBottomNav()
    }

    private fun showDatePicker(){
        var currentSelectedStartDate: Long? = null
        var currentSelectedEndDate: Long? = null

        val selectedStartDateInMillis = currentSelectedStartDate ?: System.currentTimeMillis()
        val selectedEndDateInMillis = currentSelectedEndDate ?: System.currentTimeMillis()
        val dateRange = androidx.core.util.Pair(selectedStartDateInMillis, selectedEndDateInMillis)

        MaterialDatePicker.Builder.dateRangePicker().setSelection(dateRange).build().apply {
            addOnPositiveButtonClickListener {dateRange ->
                val format = SimpleDateFormat("dd-MM-yyyy")
                val firstDate = Date(dateRange.first)
                val secondDate = Date(dateRange.second)
                val firDateFormat = format.format(firstDate)
                val secondDateFormat = format.format(secondDate)
                binding.textInitDate.text = "Inicio : ${firDateFormat}"
                binding.textEndDate.text = "Fin : ${secondDateFormat}"
            }
        }.show(requireActivity().supportFragmentManager, MaterialDatePicker::class.java.canonicalName)
    }
}
package com.example.softlogistica.ui.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.softlogistica.BaseActivity
import com.example.softlogistica.databinding.BudgetFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

private const val NUM_PAGES = 3

val tabNames = arrayOf(
    "Aceptados",
    "pendientes",
    "rechazados"
)

class BudgetFragment : Fragment() {


    private lateinit var viewModel: BudgetViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: BudgetAdapter
    private lateinit var binding : BudgetFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BudgetFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        viewPager = binding.pager
        val tabLayout = binding.tabLayoutBudget

        adapter = BudgetAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
        return view

    }

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).disableBottomNav()
    }

}
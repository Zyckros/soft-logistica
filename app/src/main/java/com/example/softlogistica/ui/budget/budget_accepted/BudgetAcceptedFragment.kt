package com.example.softlogistica.ui.budget.budget_accepted

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.softlogistica.R

class BudgetAcceptedFragment : Fragment() {

    companion object {
        fun newInstance() = BudgetAcceptedFragment()
    }

    private lateinit var viewModel: BudgetAcceptedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.budget_accepted_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
    }

}
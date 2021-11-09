package com.example.softlogistica.ui.budget.budget_pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.softlogistica.R

class BudgetPendingFragment : Fragment() {

    companion object {
        fun newInstance() = BudgetPendingFragment()
    }

    private lateinit var viewModel: BudgetPendingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.budget_pending_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
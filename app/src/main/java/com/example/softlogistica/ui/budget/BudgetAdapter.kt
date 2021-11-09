package com.example.softlogistica.ui.budget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.softlogistica.ui.budget.budget_accepted.BudgetAcceptedFragment
import com.example.softlogistica.ui.budget.budget_pending.BudgetPendingFragment

class BudgetAdapter(val activity : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(activity, lifecycle) {

    override fun getItemCount(): Int {
            return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BudgetAcceptedFragment()
            1 -> BudgetPendingFragment()
            2 -> BudgetPendingFragment()
            else -> BudgetAcceptedFragment()
        }
    }
}


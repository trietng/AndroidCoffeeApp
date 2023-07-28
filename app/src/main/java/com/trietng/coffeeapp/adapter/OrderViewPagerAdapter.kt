package com.trietng.coffeeapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.trietng.coffeeapp.HistoryFragment
import com.trietng.coffeeapp.OngoingFragment

class OrderViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OngoingFragment()
            else -> HistoryFragment()
        }
    }

}
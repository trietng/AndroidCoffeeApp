package com.trietng.coffeeapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.trietng.coffeeapp.adapter.OrderViewPagerAdapter

class MyOrderFragment : Fragment(R.layout.fragment_my_order) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayoutMyOrder = view.findViewById<TabLayout>(R.id.tab_layout_my_order)
        val viewPagerMyOrder = view.findViewById<ViewPager2>(R.id.view_pager_my_order)
        val orderViewPagerAdapter = OrderViewPagerAdapter(requireActivity())
        viewPagerMyOrder.adapter = orderViewPagerAdapter
        tabLayoutMyOrder.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPagerMyOrder.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        viewPagerMyOrder.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayoutMyOrder.selectTab(tabLayoutMyOrder.getTabAt(position))
            }
        })
    }
}
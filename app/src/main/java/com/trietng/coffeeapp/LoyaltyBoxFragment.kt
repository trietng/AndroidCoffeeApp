package com.trietng.coffeeapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.adapter.LoyaltyBoxAdapter

class LoyaltyBoxFragment : Fragment(R.layout.fragment_loyalty_box) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loyaltyBoxAdapter = LoyaltyBoxAdapter(requireContext(), requireActivity(), 3)
        val loyaltyBoxRecyclerView = view.findViewById<RecyclerView>(R.id.loyalty_box_inner)
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        loyaltyBoxRecyclerView.layoutManager = linearLayoutManager
        loyaltyBoxRecyclerView.adapter = loyaltyBoxAdapter
    }
}
package com.trietng.coffeeapp;

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.adapter.HistoryRewardsAdapter
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModel
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModelFactory

class RewardsFragment : Fragment(R.layout.fragment_rewards) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set adapter for history rewards
        val historyRewardsAdapter = HistoryRewardsAdapter()
        val historyRewardsRecyclerView = view.findViewById<RecyclerView>(R.id.history_rewards)
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        historyRewardsRecyclerView.layoutManager = linearLayoutManager
        historyRewardsRecyclerView.adapter = historyRewardsAdapter
        val totalPoints = view.findViewById<TextView>(R.id.totalPoints)

        loyaltyViewModel.getSumPoint.observe(viewLifecycleOwner) { sumPoint ->
            sumPoint?.let { totalPoints.text = it.toString() }
        }

        loyaltyViewModel.getRecent.observe(viewLifecycleOwner) { loyalties ->
            loyalties?.let { historyRewardsAdapter.submitList(it) }
        }
    }

    override fun onStart() {
        super.onStart()
        childFragmentManager.commit {
            setReorderingAllowed(true)
            add<LoyaltyBoxFragment>(R.id.loyalty_box_rewards)
        }
    }

    private val loyaltyViewModel: LoyaltyViewModel by viewModels {
        LoyaltyViewModelFactory((requireActivity().application as CoffeeApplication).repository)
    }
}

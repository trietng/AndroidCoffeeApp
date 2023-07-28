package com.trietng.coffeeapp;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
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
            sumPoint.let {
                if (it == null) {
                    totalPoints.text = "0"
                    totalLoyaltyPoint = 0
                }
                else {
                    totalPoints.text = it.toString()
                    totalLoyaltyPoint = it
                }
            }
        }

        loyaltyViewModel.getRecent.observe(viewLifecycleOwner) { loyalties ->
            loyalties?.let { historyRewardsAdapter.submitList(it) }
        }

        val dividerItemDecoration = DividerItemDecoration(
            historyRewardsRecyclerView.context,
            linearLayoutManager.orientation
        )
        historyRewardsRecyclerView.addItemDecoration(dividerItemDecoration)

        // Find reedem button
        val redeemButton = view.findViewById<TextView>(R.id.redeem_drinks)
        redeemButton.setOnClickListener {
            val intent = Intent(activity, RedeemActivity::class.java)
            intent.putExtra("total_loyalty_point", totalLoyaltyPoint)
            startActivity(intent)
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

    private var totalLoyaltyPoint = 0
}

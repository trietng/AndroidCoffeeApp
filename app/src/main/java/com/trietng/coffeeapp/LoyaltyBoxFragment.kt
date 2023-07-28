package com.trietng.coffeeapp

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.adapter.LoyaltyBoxAdapter
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModel
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModelFactory
import com.trietng.coffeeapp.database.viewmodel.UserViewModel
import com.trietng.coffeeapp.database.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoyaltyBoxFragment : Fragment(R.layout.fragment_loyalty_box) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val loyaltyBoxAdapter = LoyaltyBoxAdapter(
                requireContext(),
                requireActivity(),
                userViewModel.getCurrentNumLoyaltyCup()
            )
            val loyaltyBoxRecyclerView = view.findViewById<RecyclerView>(R.id.loyalty_box_inner)
            val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            loyaltyBoxRecyclerView.layoutManager = linearLayoutManager
            loyaltyBoxRecyclerView.adapter = loyaltyBoxAdapter
        }

    }

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((requireActivity().application as CoffeeApplication).repository)
    }

    private val loyaltyViewModel: LoyaltyViewModel by viewModels {
        LoyaltyViewModelFactory((requireActivity().application as CoffeeApplication).repository)
    }

    private var numLoyaltyCup = 0
}
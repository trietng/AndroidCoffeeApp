package com.trietng.coffeeapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.adapter.OrderListAdapter
import com.trietng.coffeeapp.database.viewmodel.OrderViewModel
import com.trietng.coffeeapp.database.viewmodel.OrderViewModelFactory

class HistoryFragment : Fragment(R.layout.fragment_history) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderListAdapter = OrderListAdapter()
        val orderListRecyclerView = view.findViewById<RecyclerView>(R.id.order_list_history)
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        orderListRecyclerView.layoutManager = linearLayoutManager
        orderListRecyclerView.adapter = orderListAdapter
        val dividerItemDecoration = DividerItemDecoration(
            orderListRecyclerView.context,
            linearLayoutManager.orientation
        )
        orderListRecyclerView.addItemDecoration(dividerItemDecoration)

        orderViewModel.getCompletedOrder.observe(viewLifecycleOwner) { orders ->
            orders?.let { orderListAdapter.submitList(it) }
        }
    }

    private val orderViewModel: OrderViewModel by viewModels {
        OrderViewModelFactory((requireActivity().application as CoffeeApplication).repository)
    }
}
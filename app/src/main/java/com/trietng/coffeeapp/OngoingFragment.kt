package com.trietng.coffeeapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.adapter.OrderListAdapter
import com.trietng.coffeeapp.database.viewmodel.CartViewModel
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModel
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModelFactory
import com.trietng.coffeeapp.database.viewmodel.OrderViewModel
import com.trietng.coffeeapp.database.viewmodel.OrderViewModelFactory
import com.trietng.coffeeapp.database.viewmodel.UserViewModel
import com.trietng.coffeeapp.database.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OngoingFragment : Fragment(R.layout.fragment_ongoing) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderListAdapter = OrderListAdapter()
        val orderListRecyclerView = view.findViewById<RecyclerView>(R.id.order_list_ongoing)
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        orderListRecyclerView.layoutManager = linearLayoutManager
        orderListRecyclerView.adapter = orderListAdapter
        val dividerItemDecoration = DividerItemDecoration(
            orderListRecyclerView.context,
            linearLayoutManager.orientation
        )
        orderListRecyclerView.addItemDecoration(dividerItemDecoration)

        orderViewModel.getOngoingOrder.observe(viewLifecycleOwner) { orders ->
            orders?.let { orderListAdapter.submitList(it) }
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val castResult = viewHolder as OrderListAdapter.OrderViewHolder
                lifecycleScope.launch {
                    val numLoyaltyCup = userViewModel.getCurrentNumLoyaltyCup()
                    val totalQuantity = orderViewModel.getTotalQuantity(castResult.orderId)
                    val newNumLoyaltyCup = (numLoyaltyCup + totalQuantity) % 8
                    val loyaltyConstant = (numLoyaltyCup + totalQuantity) / 8
                    if (loyaltyConstant > 0) {
                        loyaltyViewModel.insert(
                            "Automatic reward",
                            loyaltyConstant * 30,
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        )
                    }
                    if (newNumLoyaltyCup == 0) {
                        userViewModel.resetNumLoyaltyCup()
                    }
                    else {
                        userViewModel.updateNumLoyaltyCup(newNumLoyaltyCup)
                    }
                }
                orderViewModel.setOrderCompleted(castResult.orderId)
            }
        })
        itemTouchHelper.attachToRecyclerView(orderListRecyclerView)
    }

    private val orderViewModel: OrderViewModel by viewModels {
        OrderViewModelFactory((requireActivity().application as CoffeeApplication).repository)
    }

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((requireActivity().application as CoffeeApplication).repository)
    }

    private val loyaltyViewModel: LoyaltyViewModel by viewModels {
        LoyaltyViewModelFactory((requireActivity().application as CoffeeApplication).repository)
    }
}
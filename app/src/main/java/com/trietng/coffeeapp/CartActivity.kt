package com.trietng.coffeeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.adapter.CartListAdapter
import com.trietng.coffeeapp.database.viewmodel.CartViewModel
import com.trietng.coffeeapp.database.viewmodel.CartViewModelFactory

class CartActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_cart)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        // Total price value
        val totalPriceValue = findViewById<android.widget.TextView>(R.id.total_price_value)
        cartViewModel.getTotalPrice.observe(this) {
            it.let {
                if (it == null) {
                    totalPriceValue.text = "$0.00"
                }
                else {
                    totalPriceValue.text = "$%.2f".format(it)
                }
            }
        }

        // Set up recycler view
        val cartListAdapter = CartListAdapter(this)
        val cartRecyclerView = findViewById<RecyclerView>(R.id.cart_recycler_view)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        cartRecyclerView.layoutManager = linearLayoutManager
        cartRecyclerView.adapter = cartListAdapter
        cartViewModel.getAllCart.observe(this) {
            it.let {
                cartListAdapter.submitList(it)
            }
        }

        // Swipe left to delete functionality
        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // cartListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                cartViewModel.delete((viewHolder as CartListAdapter.CartViewHolder).cartId)
            }
        })
        itemTouchHelper.attachToRecyclerView(cartRecyclerView)

    }

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory((application as CoffeeApplication).repository)
    }
}
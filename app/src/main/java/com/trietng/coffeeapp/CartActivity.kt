package com.trietng.coffeeapp

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.adapter.CartListAdapter
import com.trietng.coffeeapp.database.dao.CartExtra
import com.trietng.coffeeapp.database.viewmodel.CartViewModel
import com.trietng.coffeeapp.database.viewmodel.CartViewModelFactory
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModel
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModelFactory
import com.trietng.coffeeapp.database.viewmodel.OrderViewModel
import com.trietng.coffeeapp.database.viewmodel.OrderViewModelFactory
import com.trietng.coffeeapp.database.viewmodel.UserViewModel
import com.trietng.coffeeapp.database.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.StringJoiner

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

        lifecycleScope.launch {
            address = userViewModel.getAddress()
        }

        // Get checkout button
        val buttonCheckout = findViewById<Button>(R.id.button_checkout)

        // Total price value
        val totalPriceValue = findViewById<TextView>(R.id.total_price_value)
        cartViewModel.getTotalPrice.observe(this) {
            it.let {
                if (it == null) {
                    totalPriceValue.text = "$0.00"
                    buttonCheckout.isEnabled = false
                }
                else {
                    totalPriceValue.text = "$%.2f".format(it)
                    buttonCheckout.isEnabled = true
                    totalPrice = it
                }
            }
        }
        cartViewModel.getTotalQuantity.observe(this) {
            it.let {
                totalQuantity = it ?: 0
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
                cart = it
            }
        }
        val itemDecoration = object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                if (position != 0) {
                    outRect.top = resources.getDimension(R.dimen.item_cart_margin_top).toInt()
                }
            }
        }
        cartRecyclerView.addItemDecoration(itemDecoration)

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
        // Add checkout onClickListener
        buttonCheckout.setOnClickListener {
            val stringJoiner = StringJoiner(",")
            for (item in cart!!) {
                stringJoiner.add("${item.name} x${item.quantity}")
            }
            orderViewModel.insert(
                stringJoiner.toString(),
                totalQuantity,
                totalPrice!!,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                0,
                address!!
            )
            cartViewModel.deleteAll()
            val intent = Intent(this, OrderSuccessActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as CoffeeApplication).repository)
    }

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory((application as CoffeeApplication).repository)
    }

    private val orderViewModel: OrderViewModel by viewModels {
        OrderViewModelFactory((application as CoffeeApplication).repository)
    }

    private var cart: List<CartExtra>? = null
    private var totalPrice: Double? = null
    private var address: String? = null
    private var totalQuantity: Int = 0
}
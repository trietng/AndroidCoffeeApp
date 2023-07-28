package com.trietng.coffeeapp

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.adapter.VoucherListAdapter
import com.trietng.coffeeapp.database.viewmodel.CartViewModel
import com.trietng.coffeeapp.database.viewmodel.CartViewModelFactory
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModel
import com.trietng.coffeeapp.database.viewmodel.LoyaltyViewModelFactory
import com.trietng.coffeeapp.database.viewmodel.VoucherViewModel
import com.trietng.coffeeapp.database.viewmodel.VoucherViewModelFactory
import com.trietng.coffeeapp.view.ButtonRedeem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RedeemActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeem)
        val totalLoyaltyPoint = intent.getIntExtra("total_loyalty_point", 0)

        // Set up toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_redeem)
        setSupportActionBar(toolbar)
        if (supportActionBar != null){
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        // Set up recycler view
        val voucherRecyclerView = findViewById<RecyclerView>(R.id.voucher_list)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val voucherListAdapter = VoucherListAdapter(this) {
            val button = it as ButtonRedeem
            if (totalLoyaltyPoint >= button.voucherExtra!!.point) {
                voucherViewModel.deleteVoucher(button.voucherExtra!!.voucherId)
                loyaltyViewModel.insert(
                    "Redeem voucher id #${button.voucherExtra!!.voucherId}",
                    -button.voucherExtra!!.point,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                cartViewModel.insert(
                    button.voucherExtra!!.coffeeId,
                    0,
                    1,
                    1,
                    2,
                    button.voucherExtra!!.quantity,
                    0
                )
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        voucherRecyclerView.layoutManager = linearLayoutManager
        voucherRecyclerView.adapter = voucherListAdapter

        voucherViewModel.getAllVoucher.observe(this) { vouchers ->
            vouchers.let {
                voucherListAdapter.submitList(it)
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
                    outRect.top = resources.getDimension(R.dimen.item_voucher_margin_top).toInt()
                }
            }
        }
        voucherRecyclerView.addItemDecoration(itemDecoration)
    }

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory((application as CoffeeApplication).repository)
    }

    private val voucherViewModel: VoucherViewModel by viewModels {
        VoucherViewModelFactory((application as CoffeeApplication).repository)
    }

    private val loyaltyViewModel: LoyaltyViewModel by viewModels {
        LoyaltyViewModelFactory((application as CoffeeApplication).repository)
    }
}
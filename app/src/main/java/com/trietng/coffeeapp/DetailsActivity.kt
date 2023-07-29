package com.trietng.coffeeapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trietng.coffeeapp.database.viewmodel.CartViewModel
import com.trietng.coffeeapp.database.viewmodel.CartViewModelFactory

class DetailsActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ButtonCartFragment>(R.id.button_cart_details)
            }
        }

        // Set up toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_details)
        setSupportActionBar(toolbar)
        if (supportActionBar != null){
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        // Load selection from intent
        val coffeeId = intent.getIntExtra("coffee_id", 0)

        val imageDrawableName = intent.getStringExtra("coffee_image_filename").toString()
        val imageId = resources.getIdentifier(imageDrawableName, "drawable", packageName)
        val imageDrawable = ResourcesCompat.getDrawable(resources, imageId, null)
        findViewById<ImageView>(R.id.details_image).setImageDrawable(imageDrawable)

        val coffeeName = intent.getStringExtra("coffee_name").toString()
        findViewById<TextView>(R.id.text_coffee_name).text = coffeeName

        val detail = Detail(
            coffeeId,
            intent.getDoubleExtra("coffee_price", 0.0),
            0,
            1,
            1,
            2,
            1
        )

        // Quantity default value is 1
        val textQuantity = findViewById<TextView>(R.id.text_quantity)
        val textTotalAmountValue = findViewById<TextView>(R.id.text_total_amount_value)

        textQuantity.text = "1"
        textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())

        // Set up quantity buttons
        val buttonIncreaseQuantity = findViewById<TextView>(R.id.button_increase_quantity)
        val buttonDecreaseQuantity = findViewById<TextView>(R.id.button_decrease_quantity)

        buttonIncreaseQuantity.setOnClickListener {
            if (detail.quantity < 5)  {
                ++detail.quantity
                textQuantity.text = detail.quantity.toString()
                textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
            }
        }

        buttonDecreaseQuantity.setOnClickListener {
            if (detail.quantity > 1) {
                --detail.quantity
                textQuantity.text = detail.quantity.toString()
                textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
            }
        }

        // Get all bottom navigation views
        val bottomNavigationView = arrayListOf<BottomNavigationView>()
        bottomNavigationView.add(findViewById(R.id.detail_selector_shot))
        bottomNavigationView.add(findViewById(R.id.detail_selector_select))
        bottomNavigationView.add(findViewById(R.id.detail_selector_size))
        bottomNavigationView.add(findViewById(R.id.detail_selector_ice))

        bottomNavigationView[0].setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_shot_single -> {
                    detail.shot = 0
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                R.id.menu_shot_double -> {
                    detail.shot = 1
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                else -> false
            }
        }


        bottomNavigationView[1].setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_select_hot -> {
                    detail.type = 0
                    detail.ice = 0
                    findViewById<RelativeLayout>(R.id.detail_ice).visibility = View.GONE
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                R.id.menu_select_cold -> {
                    detail.type = 1
                    detail.type = 1
                    findViewById<RelativeLayout>(R.id.detail_ice).visibility = View.VISIBLE
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                else -> false
            }
        }

        bottomNavigationView[2].setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_size_small -> {
                    detail.size = 0
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                R.id.menu_size_normal -> {
                    detail.size = 1
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                R.id.menu_size_large -> {
                    detail.size = 2
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                else -> false
            }
        }

        bottomNavigationView[3].setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_ice_less -> {
                    detail.ice = 1
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                R.id.menu_ice_normal -> {
                    detail.ice = 2
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                R.id.menu_ice_more -> {
                    detail.ice = 3
                    textTotalAmountValue.text = "$%.2f".format(detail.getFinalPrice())
                    true
                }
                else -> false
            }
        }

        // Set up bottom navigation views
        bottomNavigationView[0].selectedItemId = R.id.menu_shot_single
        bottomNavigationView[1].selectedItemId = R.id.menu_select_cold
        bottomNavigationView[2].selectedItemId = R.id.menu_size_normal
        bottomNavigationView[3].selectedItemId = R.id.menu_ice_normal

        // Set up add to cart button
        val buttonAddToCart = findViewById<TextView>(R.id.button_add_to_cart)

        buttonAddToCart.setOnClickListener {
            val cart = cartViewModel.findItem(coffeeId, detail.shot, detail.type, detail.size, detail.ice)
            if (cart != null) {
                if (cart.quantity < 5) {
                    if (detail.quantity + cart.quantity <= 5) {
                        cartViewModel.setQuantity(cart.cartId, detail.quantity + cart.quantity)
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(applicationContext, "Number of coffee item in Cart must not exceed 5", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                cartViewModel.insert(coffeeId, detail.shot, detail.type, detail.size, detail.ice, detail.quantity, 1)
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

//        onBackPressedDispatcher.addCallback(this) {
//            finish()
//        }
    }

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory((application as CoffeeApplication).repository)
    }

    private data class Detail(
        val coffeeId: Int,
        val basePrice: Double,
        var shot: Int,
        var type: Int,
        var size: Int,
        var ice: Int,
        var quantity: Int
    ) {
        fun getFinalPrice(): Double {
            return (basePrice + shot + 0.5 * type + 0.5 * size) * quantity
        }
    }
}
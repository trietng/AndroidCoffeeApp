package com.trietng.coffeeapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
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

        // Quantity default value is 1
        val textQuantity = findViewById<TextView>(R.id.text_quantity)
        val textTotalAmountValue = findViewById<TextView>(R.id.text_total_amount_value)
        val price = intent.getDoubleExtra("coffee_price", 0.0) + 1.0

        textQuantity.text = "1"
        textTotalAmountValue.text = "$%.2f".format(price)

        // Set up quantity buttons
        val buttonIncreaseQuantity = findViewById<TextView>(R.id.button_increase_quantity)
        val buttonDecreaseQuantity = findViewById<TextView>(R.id.button_decrease_quantity)

        buttonIncreaseQuantity.setOnClickListener {
            if (quantity < 5)  {
                ++quantity
                textQuantity.text = quantity.toString()
                textTotalAmountValue.text = "$%.2f".format(price * quantity)
            }
        }

        buttonDecreaseQuantity.setOnClickListener {
            if (quantity > 1) {
                --quantity
                textQuantity.text = quantity.toString()
                textTotalAmountValue.text = "$%.2f".format(price * quantity)
            }
        }

        // Set up add to cart button
        val buttonAddToCart = findViewById<TextView>(R.id.button_add_to_cart)

        buttonAddToCart.setOnClickListener {
            val card = cartViewModel.findItem(coffeeId, 0, 1, 1, 2)
            if (card != null) {
                if (card.quantity < 5) {
                    if (quantity + card.quantity <= 5) {
                        cartViewModel.setQuantity(card.cartId, quantity + card.quantity)
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this, "Number of coffee item in Cart must not exceed 5", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else {
                cartViewModel.insert(coffeeId, 0, 1, 1, 2, quantity)
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

//        onBackPressedDispatcher.addCallback(this) {
//            finish()
//        }
    }

    private var quantity: Int = 1

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory((application as CoffeeApplication).repository)
    }
}
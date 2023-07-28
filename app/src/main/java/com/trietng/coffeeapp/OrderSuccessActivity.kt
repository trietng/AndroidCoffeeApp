package com.trietng.coffeeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OrderSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_successs)

        findViewById<Button>(R.id.button_track_my_order).setOnClickListener {
            val newIntent = Intent(this, MainActivity::class.java)
            // Add data to intent
            newIntent.putExtra("main_activity_fragment_id", R.id.nav_item_my_orders)
            // Start new activity
            startActivity(newIntent)
            finish()
        }
    }
}
package com.trietng.coffeeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.add
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_container)
            }
        }
        findViewById<BottomNavigationView>(R.id.bottom_nav_bar).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_item_home -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container, HomeFragment())
                    }
                    true
                }
                R.id.nav_item_my_orders -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container, MyOrdersFragment())
                    }
                    true
                }
                R.id.nav_item_rewards -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container, RewardsFragment())
                    }
                    true
                }
                else -> false
            }
        }
    }
}
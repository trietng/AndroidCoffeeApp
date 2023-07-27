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
                    if (currentFragmentId != R.id.nav_item_home) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(R.id.fragment_container, HomeFragment())
                        }
                        currentFragmentId = R.id.nav_item_home
                    }
                    true
                }
                R.id.nav_item_my_orders -> {
                    if (currentFragmentId != R.id.nav_item_my_orders) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(R.id.fragment_container, MyOrderFragment())
                        }
                        currentFragmentId = R.id.nav_item_my_orders
                    }
                    true
                }
                R.id.nav_item_rewards -> {
                    if (currentFragmentId != R.id.nav_item_rewards) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(R.id.fragment_container, RewardsFragment())
                        }
                        currentFragmentId = R.id.nav_item_rewards
                    }
                    true
                }
                else -> false
            }
        }

        // Set fragment from intent
        val fragmentId = intent.getIntExtra("main_activity_fragment_id", -1)
        if (fragmentId != -1) {
            when (fragmentId) {
                R.id.nav_item_my_orders -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container, MyOrderFragment())
                    }
                    findViewById<BottomNavigationView>(R.id.bottom_nav_bar).selectedItemId =
                        R.id.nav_item_my_orders
                    currentFragmentId = R.id.nav_item_my_orders
                }
            }
        }
    }

    private var currentFragmentId = R.id.nav_item_home

}
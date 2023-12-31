package com.trietng.coffeeapp

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.adapter.CoffeeSelectionAdapter
import com.trietng.coffeeapp.database.viewmodel.CoffeeViewModel
import com.trietng.coffeeapp.database.viewmodel.CoffeeViewModelFactory
import com.trietng.coffeeapp.database.viewmodel.UserViewModel
import com.trietng.coffeeapp.database.viewmodel.UserViewModelFactory
import java.time.LocalDateTime

class HomeFragment : Fragment(R.layout.fragment_home) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get current time
        val currentTime = LocalDateTime.now().hour
        val greeting = view.findViewById<TextView>(R.id.greeting)
        when (currentTime) {
            in 5..11 -> greeting.text = "Good morning"
            in 12..17 -> greeting.text = "Good afternoon"
            else -> greeting.text = "Good evening"
        }


        // Set adapter for coffee selection
        val coffeeSelectionAdapter = CoffeeSelectionAdapter(requireActivity())
        val coffeeSelectionRecyclerView = view.findViewById<RecyclerView>(R.id.menu_box_inner)
        val spanCount = 2
        val gridLayoutManager = GridLayoutManager(requireContext(), spanCount, GridLayoutManager.VERTICAL, false)
        coffeeSelectionRecyclerView.layoutManager = gridLayoutManager
        coffeeSelectionRecyclerView.adapter = coffeeSelectionAdapter
        coffeeViewModel.getAllCoffee.observe(viewLifecycleOwner) {
            it.let {
                coffeeSelectionAdapter.submitList(it)
            }
        }


        val spacing = (8 * resources.displayMetrics.density).toInt()

        val itemDecoration = object: RecyclerView.ItemDecoration()  {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                val column = position % spanCount
                val row = position / spanCount
                if (column == 0) {
                    outRect.right = spacing
                }
                else if (column == spanCount - 1) {
                    outRect.left = spacing
                }
                else {
                    outRect.left = spacing
                    outRect.right = spacing
                }
                if (row == 0) {
                    outRect.bottom = spacing
                }
                else if (row == coffeeSelectionAdapter.itemCount / spanCount - 1) {
                    outRect.top = spacing
                }
                else {
                    outRect.top = spacing
                    outRect.bottom = spacing
                }

            }
        }
        coffeeSelectionRecyclerView.addItemDecoration(itemDecoration)


        view.findViewById<ImageButton>(R.id.button_profile).setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        val fullname = view.findViewById<TextView>(R.id.username_home)
        userViewModel.getFullname.observe(viewLifecycleOwner) {
            it.let {
                fullname.text = it
            }
        }

        val menuBoxOuter = view.findViewById<RelativeLayout>(R.id.menu_box_outer)
        menuBoxOuter.setOnTouchListener (object: OnSwipeTouchListener(requireContext()) {
            override fun onSwipeUp() {
                animate(84)
            }

            override fun onSwipeDown() {
                animate(264)
            }

            fun animate(topMarginEndDP: Int) {
                val layoutParams = menuBoxOuter.layoutParams as RelativeLayout.LayoutParams
                val topMarginStart = layoutParams.topMargin
                val topMarginEnd = (topMarginEndDP * resources.displayMetrics.density).toInt()
                if (topMarginStart == topMarginEnd) return
                ValueAnimator.ofInt(topMarginStart, topMarginEnd).apply {
                    duration = 250
                    addUpdateListener { animation ->
                        layoutParams.setMargins(0, (animation.animatedValue as Int), 0, 0)
                        menuBoxOuter.layoutParams = layoutParams
                    }
                    start()
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()

        // Add nested fragments
        childFragmentManager.commit {
            setReorderingAllowed(true)
            add<ButtonCartFragment>(R.id.button_cart_home)
            add<LoyaltyBoxFragment>(R.id.loyalty_box_home)
        }
    }

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((requireActivity().application as CoffeeApplication).repository)
    }

    private val coffeeViewModel: CoffeeViewModel by viewModels {
        CoffeeViewModelFactory((requireActivity().application as CoffeeApplication).repository)
    }
}
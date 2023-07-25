package com.trietng.coffeeapp.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.R

class LoyaltyBoxAdapter(context: Context, activity: Activity, numSelectedItem: Int) :
    RecyclerView.Adapter<LoyaltyBoxAdapter.ViewHolder>() {

    private var activity: Activity
    private var numSelectedItem: Int
    private var inflater: LayoutInflater

    init {
        this.activity = activity
        this.numSelectedItem = numSelectedItem
        this.inflater = LayoutInflater.from(context)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemLoyaltyCup: ImageView? = null
        init {
            itemLoyaltyCup = itemView.findViewById(R.id.item_loyalty_cup)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view0 = inflater.inflate(R.layout.item_loyalty_cup, parent, false)
        return ViewHolder(view0)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < numSelectedItem) {
            holder.itemLoyaltyCup?.setImageResource(R.drawable.ic_coffee_cup_selected)
        } else {
            holder.itemLoyaltyCup?.setImageResource(R.drawable.ic_coffee_cup)
        }
    }
}
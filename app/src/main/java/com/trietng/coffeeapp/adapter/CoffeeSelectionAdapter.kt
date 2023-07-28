package com.trietng.coffeeapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.DetailsActivity
import com.trietng.coffeeapp.R
import com.trietng.coffeeapp.database.entity.Coffee
import com.trietng.coffeeapp.database.entity.Loyalty

class CoffeeSelectionAdapter(activity: Activity) :
    ListAdapter<Coffee, CoffeeSelectionAdapter.CoffeeViewHolder>(CoffeeDiff()) {

    private val activity: Activity

    init {
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder{
        return CoffeeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = getItem(position)
        val imageId = activity.resources.getIdentifier(
            coffee.imageFilename, "drawable", activity.packageName)
        val imageDrawable = ResourcesCompat.getDrawable(
            activity.resources, imageId, null)
        holder.itemCoffee.getChildAt(0).background = imageDrawable
        val textView = holder.itemCoffee.getChildAt(1) as TextView
        textView.text = coffee.name
        holder.itemCoffee.setOnClickListener {
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra("coffee_id", coffee.coffeeId)
            intent.putExtra("coffee_name", coffee.name)
            intent.putExtra("coffee_image_filename", coffee.imageFilename)
            intent.putExtra("coffee_price", coffee.price)
            activity.startActivity(intent)
        }
    }

    class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemCoffee: LinearLayout = itemView.findViewById(R.id.item_coffee)

        companion object {
            fun create(parent: ViewGroup): CoffeeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_coffee, parent, false)
                return CoffeeViewHolder(view)
            }
        }
    }

    class CoffeeDiff : DiffUtil.ItemCallback<Coffee>() {
        override fun areItemsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
            return oldItem.coffeeId == newItem.coffeeId
        }
    }

}
package com.trietng.coffeeapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.R
import com.trietng.coffeeapp.database.dao.CartExtra

class CartListAdapter(activity: Activity) :
    ListAdapter<CartExtra, CartListAdapter.CartViewHolder>(CartDiff()) {

    private val activity: Activity

    init {
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = getItem(position)
        holder.cartId = cart.cartId
        val imageId = activity.resources.getIdentifier(cart.imageFilename, "drawable", activity.packageName)
        val imageDrawable = ResourcesCompat.getDrawable(activity.resources, imageId, null)
        holder.itemCart.findViewById<ImageView>(R.id.item_cart_image).setImageDrawable(imageDrawable)
        holder.itemCart.findViewById<TextView>(R.id.item_cart_name).text = cart.name
        val content =
            StringBuilder().append(
                when(cart.shot) {
                    0 -> "single"
                    else -> "double"
                }
            ).append(
                when(cart.type) {
                    0 -> " | hot"
                    else -> " | iced"
                }
            ).append(
                when(cart.size) {
                    0 -> " | small"
                    1 -> " | medium"
                    else -> " | large"
                }
            ).append(
                when(cart.ice) {
                    0 -> ""
                    1 -> " | less ice"
                    2 -> " | normal ice"
                    else -> " | full ice"
                }
            ).toString()
        holder.itemCart.findViewById<TextView>(R.id.item_cart_content).text = content
        holder.price = cart.price
        holder.itemCart.findViewById<TextView>(R.id.item_cart_price).text = "$%.2f".format(holder.price)
        holder.itemCart.findViewById<TextView>(R.id.item_cart_quantity).text = "${cart.quantity}"
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cartId: Int = 0
        var price: Double = 0.0
        val itemCart: RelativeLayout = itemView.findViewById(R.id.item_cart)

        companion object {
            fun create(parent: ViewGroup): CartViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cart, parent, false)
                return CartViewHolder(view)
            }
        }
    }

    class CartDiff : DiffUtil.ItemCallback<CartExtra>() {
        override fun areItemsTheSame(oldItem: CartExtra, newItem: CartExtra): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CartExtra, newItem: CartExtra): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

}
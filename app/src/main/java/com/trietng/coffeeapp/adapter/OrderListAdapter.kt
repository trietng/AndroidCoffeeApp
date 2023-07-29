package com.trietng.coffeeapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.R
import com.trietng.coffeeapp.database.entity.Order

class OrderListAdapter :
    ListAdapter<Order, OrderListAdapter.OrderViewHolder>(OrderDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.orderId = order.orderId
        holder.totalAmount = order.totalPrice
        holder.itemOrder.findViewById<TextView>(R.id.item_order_datetime).text = order.orderedTime
        holder.itemOrder.findViewById<TextView>(R.id.item_order_content).text = order.content
        holder.itemOrder.findViewById<TextView>(R.id.item_order_address).text = order.address
        holder.itemOrder.findViewById<TextView>(R.id.item_order_price).text = "$%.2f".format(order.totalPrice)
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderId: Int = -1
        var totalAmount: Double = 0.0
        val itemOrder: RelativeLayout = itemView.findViewById(R.id.item_order)

        companion object {
            fun create(parent: ViewGroup): OrderViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_order, parent, false)
                return OrderViewHolder(view)
            }
        }
    }

    class OrderDiff : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }
    }
}
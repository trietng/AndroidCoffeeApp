package com.trietng.coffeeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.R
import com.trietng.coffeeapp.database.entity.Loyalty


class HistoryRewardsAdapter :
    ListAdapter<Loyalty, HistoryRewardsAdapter.LoyaltyViewHolder>(LoyaltyDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoyaltyViewHolder {
        return LoyaltyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LoyaltyViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class LoyaltyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val loyaltyItemView: RelativeLayout = itemView.findViewById(R.id.item_reward)

        fun bind(loyalty: Loyalty) {
            loyaltyItemView.findViewById<TextView>(R.id.item_reward_name).text = loyalty.content
            loyaltyItemView.findViewById<TextView>(R.id.item_reward_datetime).text = loyalty.timeAdded
            loyaltyItemView.findViewById<TextView>(R.id.item_reward_points).text = loyalty.point.toString()
        }

        companion object {
            fun create(parent: ViewGroup): LoyaltyViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_reward, parent, false)
                return LoyaltyViewHolder(view)
            }
        }
    }

    class LoyaltyDiff : DiffUtil.ItemCallback<Loyalty>() {
        override fun areItemsTheSame(oldItem: Loyalty, newItem: Loyalty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Loyalty, newItem: Loyalty): Boolean {
            return oldItem.content == newItem.content &&
            oldItem.timeAdded == newItem.timeAdded
        }
    }
}




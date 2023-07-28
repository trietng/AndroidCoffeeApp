package com.trietng.coffeeapp.adapter

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trietng.coffeeapp.R
import com.trietng.coffeeapp.database.dao.VoucherExtra
import com.trietng.coffeeapp.view.ButtonRedeem

class VoucherListAdapter(activity: Activity, onClickListener: View.OnClickListener) :
    ListAdapter<VoucherExtra, VoucherListAdapter.VoucherViewHolder>(VoucherDiff()) {
    private val activity: Activity
    private val onClickListener: View.OnClickListener

    init {
        this.activity = activity
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {
        return VoucherViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        val voucher = getItem(position)
        val imageId = activity.resources.getIdentifier(
            voucher.imageFilename, "drawable", activity.packageName)
        holder.itemVoucher.findViewById<ImageView>(R.id.item_voucher_image).setImageResource(imageId)
        holder.itemVoucher.findViewById<TextView>(R.id.item_voucher_name).text = voucher.name
        holder.itemVoucher.findViewById<TextView>(R.id.item_voucher_expiration).text = voucher.expirationTime
        val redeemButton = holder.itemVoucher.findViewById<ButtonRedeem>(R.id.item_voucher_redeem)
        redeemButton.voucherExtra = voucher
        redeemButton.text = "${voucher.point} pts"
        // Add callback to redeem button
        redeemButton.setOnClickListener(onClickListener)
    }

    class VoucherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemVoucher: RelativeLayout = itemView.findViewById(R.id.item_voucher)

        companion object {
            fun create(parent: ViewGroup): VoucherViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_voucher, parent, false)
                return VoucherViewHolder(view)
            }
        }
    }

    // This is bad boilerplate code

    class VoucherDiff : DiffUtil.ItemCallback<VoucherExtra>() {
        override fun areItemsTheSame(oldItem: VoucherExtra, newItem: VoucherExtra): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: VoucherExtra, newItem: VoucherExtra): Boolean {
            return oldItem.voucherId == newItem.voucherId
        }
    }
}
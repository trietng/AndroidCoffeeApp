package com.trietng.coffeeapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order")
class Order (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "order_id") val orderId: Int,
    // I don't want to deal with complex relationships, so I just store the content of the order
    // Storage inefficient, but it's fine for now
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "total_price") val totalPrice: Double,
    @ColumnInfo(name = "ordered_time") val orderedTime: String,
    @ColumnInfo(name = "status") val status: Int, // 0: on-going, 1: completed
    @ColumnInfo(name = "address") val address: String
)
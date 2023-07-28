package com.trietng.coffeeapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class Cart (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "cart_id") val cartId: Int,
    @ColumnInfo(name = "coffee_id") val coffeeId: Int,
    @ColumnInfo(name = "shot") val shot: Int, // 0: single, 1: double
    @ColumnInfo(name = "type") val type: Int, // 0: hot, 1: iced
    @ColumnInfo(name = "size") val size: Int,// 0: small, 1: medium, 2: large
    @ColumnInfo(name = "ice") val ice: Int, // 0: no ice, 1: less ice, 2: normal ice, 3: full ice
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "payment_status") val name: Int, // 0: voucher applied, 1: voucher not applied
)
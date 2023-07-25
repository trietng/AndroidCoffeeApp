package com.trietng.coffeeapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "voucher")
class Voucher (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "voucher_id") val voucherId: Int,
    @ColumnInfo(name = "coffee_id") val coffeeId: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "expiration_time") val expirationTime: String,
    @ColumnInfo(name = "point") val point: Int
)
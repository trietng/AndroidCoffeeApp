package com.trietng.coffeeapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loyalty")
class Loyalty (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "loyalty_id") val loyaltyId: Int,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "point") val point: Int,
    @ColumnInfo(name = "time_added") val timeAdded: String
)
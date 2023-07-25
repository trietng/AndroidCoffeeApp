package com.trietng.coffeeapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee")
class Coffee (
    @PrimaryKey @ColumnInfo(name = "coffee_id") val coffeeId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "image_filename") val imageFilename: String?
)
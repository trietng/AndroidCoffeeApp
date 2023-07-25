package com.trietng.coffeeapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User (
    @PrimaryKey @ColumnInfo(name = "user_id") val userId: Int, // There is only one user, so id is always 0
    @ColumnInfo(name = "fullname") val fullname: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "address") val address: String
)
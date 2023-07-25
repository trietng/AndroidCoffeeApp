package com.trietng.coffeeapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trietng.coffeeapp.database.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    // Get user
    @Query("select * from user")
    fun getUser(): Flow<User>

    // Update full name
    @Query("update user set fullname = :fullname")
    suspend fun updateFullname(fullname: String)

    // Update phone
    @Query("update user set phone = :phone")
    suspend fun updatePhone(phone: String)

    // Update address
    @Query("update user set address = :address")
    suspend fun updateAddress(address: String)

    // Update email
    @Query("update user set email = :email")
    suspend fun updateEmail(email: String)
}
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

    // Increase number of loyalty cup by 1
    @Query("update user set num_loyalty_cup = :numLoyaltyCup")
    suspend fun updateNumLoyaltyCup(numLoyaltyCup: Int)

    // Reset number of loyalty cup to 0
    @Query("update user set num_loyalty_cup = 0")
    suspend fun resetNumLoyaltyCup()

    // Get user
    @Query("select * from user")
    fun getUser(): Flow<User>

    // Get user fullname
    @Query("select fullname from user")
    fun getFullname(): Flow<String>

    // Get current number of loyalty cup
    @Query("select num_loyalty_cup from user")
    suspend fun getCurrentNumLoyaltyCup(): Int

    // Get address
    @Query("select address from user")
    suspend fun getAddress(): String

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
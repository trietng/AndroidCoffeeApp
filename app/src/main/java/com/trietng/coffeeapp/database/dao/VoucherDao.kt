package com.trietng.coffeeapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trietng.coffeeapp.database.entity.Voucher
import kotlinx.coroutines.flow.Flow

@Dao
interface VoucherDao {

    // Insert a new entry to the voucher table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(voucher: Voucher)

    // Count the number of entries in the voucher table
    @Query("select count(*) from voucher")
    suspend fun count(): Int

    // Get all vouchers
    @Query("select * from voucher")
    fun getAllVoucher(): Flow<List<Voucher>>

    // Remove a voucher from the voucher table
    @Query("delete from voucher where voucher_id = :id")
    suspend fun delete(id: Int)
}
package com.trietng.coffeeapp.database.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
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
    @Query("select voucher.voucher_id, voucher.coffee_id, coffee.name, coffee.image_filename, voucher.quantity, voucher.expiration_time, voucher.point from voucher left join coffee on voucher.coffee_id = coffee.coffee_id")
    fun getAllVoucher(): Flow<List<VoucherExtra>>

    // Remove a voucher from the voucher table
    @Query("delete from voucher where voucher_id = :id")
    suspend fun delete(id: Int)
}

class VoucherExtra (
    @ColumnInfo(name = "voucher_id") val voucherId: Int,
    @ColumnInfo(name = "coffee_id") val coffeeId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_filename") val imageFilename: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "expiration_time") val expirationTime: String,
    @ColumnInfo(name = "point") val point: Int
)
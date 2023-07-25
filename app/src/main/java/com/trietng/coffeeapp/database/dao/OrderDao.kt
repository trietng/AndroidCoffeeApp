package com.trietng.coffeeapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.trietng.coffeeapp.database.entity.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    // Set an ongoing order to completed
    @Query("update `order` set status = 1 where order_id = :orderId")
    suspend fun setOrderCompleted(orderId: Int)

    // Insert a new order
    @Query("insert into `order` (content, total_price, ordered_time, status, address) values (:content, :totalPrice, :orderedTime, :status, :address)")
    suspend fun insert(content: String, totalPrice: Double, orderedTime: String, status: Int, address: String)

    // Get 10 most recent completed order with their total price
    @Query("select * from `order` where status = 1 order by ordered_time desc limit 10")
    fun getCompletedOrder(): Flow<List<Order>>

    // Get 10 most recent ongoing order with their total price
    @Query("select * from `order` where status = 0 order by ordered_time desc limit 10")
    fun getOngoingOrder(): Flow<List<Order>>
}


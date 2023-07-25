package com.trietng.coffeeapp.database.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Query
import com.trietng.coffeeapp.database.entity.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    // Conflict check is guaranteed else where
    // Add a new entry to the cart table
    @Query("insert into cart (coffee_id, shot, type, size, ice, quantity) values (:coffeeId, :shot, :type, :size, :ice, :quantity)")
    suspend fun insert(coffeeId: Int, shot: Int, type: Int, size: Int, ice: Int, quantity: Int)

    // Get all entries from the cart table
    @Query("select *, coffee.name, coffee.image_filename, cart.quantity * (coffee.price + cart.shot + 0.5 * cart.type + 0.5 * cart.size) as price from cart left join coffee on cart.coffee_id = coffee.coffee_id")
    fun getAllCart(): Flow<List<CartExtra>>

    // Get total price
    @Query("select sum(cart.quantity * (coffee.price + cart.shot + 0.5 * cart.type + 0.5 * cart.size)) from cart left join coffee on cart.coffee_id = coffee.coffee_id")
    fun getTotalPrice(): Flow<Double?>

    // Get total quantity
    @Query("select sum(cart.quantity) from cart")
    fun getTotalQuantity(): Flow<Int?>

    // Delete item from the cart table
    @Query("delete from cart where cart_id = :cartId")
    suspend fun delete(cartId: Int)

    // Find item from the cart table
    @Query("select cart_id, quantity from cart where coffee_id = :coffeeId and shot = :shot and type = :type and size = :size and ice = :ice")
    suspend fun findItem(coffeeId: Int, shot: Int, type: Int, size: Int, ice: Int): CartMinimal?

    // Set quantity by cartId
    @Query("update cart set quantity = :quantity where cart_id = :cartId")
    suspend fun setQuantity(cartId: Int, quantity: Int)

    // Delete all items from the cart table
    @Query("delete from cart")
    suspend fun deleteAll()

}

data class CartExtra (
    @ColumnInfo(name = "cart_id") val cartId: Int,
    @ColumnInfo(name = "coffee_id") val coffeeId: Int,
    @ColumnInfo(name = "shot") val shot: Int, // 0: single, 1: double
    @ColumnInfo(name = "type") val type: Int, // 0: hot, 1: iced
    @ColumnInfo(name = "size") val size: Int,// 0: small, 1: medium, 2: large
    @ColumnInfo(name = "ice") val ice: Int, // 0: no ice, 1: less ice, 2: normal ice, 3: full ice
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_filename") val imageFilename: String,
    @ColumnInfo(name = "price") val price: Double
)

data class CartMinimal (
    @ColumnInfo(name = "cart_id") val cartId: Int,
    @ColumnInfo(name = "quantity") val quantity: Int
)
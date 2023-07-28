package com.trietng.coffeeapp.database

import com.trietng.coffeeapp.database.dao.CartDao
import com.trietng.coffeeapp.database.dao.CartMinimal
import com.trietng.coffeeapp.database.dao.CoffeeDao
import com.trietng.coffeeapp.database.dao.LoyaltyDao
import com.trietng.coffeeapp.database.dao.OrderDao
import com.trietng.coffeeapp.database.dao.UserDao
import com.trietng.coffeeapp.database.dao.VoucherDao

// Future proofing

class CoffeeRepository(
    private val userDao: UserDao,
    private val coffeeDao: CoffeeDao,
    private val cartDao: CartDao,
    private val orderDao: OrderDao,
    private val loyaltyDao: LoyaltyDao,
    private val voucherDao: VoucherDao) {

    // Wrap all methods from UserDao.kt inside this class
    // Get user
    val getUser = userDao.getUser()

    // Get user fullname
    val getUserFullname = userDao.getFullname()

    suspend fun getUserAddress(): String {
        return userDao.getAddress()
    }

    // Increase number of loyalty cup by 1
    suspend fun updateNumLoyaltyCup(numLoyaltyCup: Int) {
        userDao.updateNumLoyaltyCup(numLoyaltyCup)
    }

    // Reset number of loyalty cup to 0
    suspend fun resetNumLoyaltyCup() {
        userDao.resetNumLoyaltyCup()
    }

    // Get current number of loyalty cup
    suspend fun getCurrentNumLoyaltyCup(): Int {
        return userDao.getCurrentNumLoyaltyCup()
    }

    // Update full name
    suspend fun updateUserFullname(fullname: String) {
        userDao.updateFullname(fullname)
    }

    // Update phone
    suspend fun updateUserPhone(phone: String) {
        userDao.updatePhone(phone)
    }

    // Update address
    suspend fun updateUserAddress(address: String) {
        userDao.updateAddress(address)
    }

    // Update email
    suspend fun updateUserEmail(email: String) {
        userDao.updateEmail(email)
    }

    // Get all coffee
    val getAllCoffeeItem= coffeeDao.getAllCoffee()
    // Wrap all methods from CartDao.kt inside this class
    // Add a new entry to the cart table
    suspend fun insertCartItem(coffeeId: Int, shot: Int, type: Int, size: Int, ice: Int, quantity: Int, paymentStatus: Int) {
        cartDao.insert(coffeeId, shot, type, size, ice, quantity, paymentStatus)
    }

    // Get total quantity
    val getCartTotalQuantity = cartDao.getTotalQuantity()

    // Get total point
    val getCartTotalPrice = cartDao.getTotalPrice()

    // Get all entries from the cart table
    val getAllCartItem = cartDao.getAllCart()

    // Delete item from the cart table by cartId
    suspend fun deleteCartItem(cartId: Int) {
        cartDao.delete(cartId)
    }

    // Delete all items from the cart table
    suspend fun deleteAllCartItem() {
        cartDao.deleteAll()
    }

    // Find item from the cart table
    suspend fun findCartItem(coffeeId: Int, shot: Int, type: Int, size: Int, ice: Int): CartMinimal? {
        return cartDao.findItem(coffeeId, shot, type, size, ice)
    }

    suspend fun setCartItemQuantity(cartId: Int, amount: Int) {
        cartDao.setQuantity(cartId, amount)
    }

    // Add OrderDao.kt methods here
    // Set an ongoing order to completed
    suspend fun setOrderCompleted(orderId: Int) {
        orderDao.setOrderCompleted(orderId)
    }

    // Get total quantity of an order
    suspend fun getOrderTotalQuantity(orderId: Int): Int {
        return orderDao.getTotalQuantity(orderId)
    }

    // Insert a new order
    suspend fun insertOrderItem(content: String, totalQuantity: Int, totalPrice: Double, orderedTime: String, status: Int, address: String) {
        orderDao.insert(content, totalQuantity, totalPrice, orderedTime, status, address)
    }

    // Get 10 most recent completed order with their total price
    val getCompletedOrder = orderDao.getCompletedOrder()

    // Get 10 most recent ongoing order with their total price
    val getOngoingOrder = orderDao.getOngoingOrder()

    suspend fun insertLoyaltyItem(content: String, point: Int, timeAdded: String) {
        loyaltyDao.insert(content, point, timeAdded)
    }

    // Get 10 most recent entries from the loyalty table
    val getRecentLoyaltyItem = loyaltyDao.getRecent()

    // Get sum of points from the loyalty table
    val getSumLoyaltyPoint = loyaltyDao.getSumPoint()

    // Get current sum of points from the loyalty table
    suspend fun getCurrentSumLoyaltyPoint(): Int? {
        return loyaltyDao.getCurrentSumPoint()
    }

    // Get all vouchers
    val getAllVoucher = voucherDao.getAllVoucher()

    // Remove a voucher from the voucher table
    suspend fun deleteVoucher(voucherId: Int) {
        voucherDao.delete(voucherId)
    }
}
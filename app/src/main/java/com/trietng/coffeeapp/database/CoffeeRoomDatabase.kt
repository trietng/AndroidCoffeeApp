package com.trietng.coffeeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.trietng.coffeeapp.database.dao.CartDao
import com.trietng.coffeeapp.database.dao.CoffeeDao
import com.trietng.coffeeapp.database.dao.LoyaltyDao
import com.trietng.coffeeapp.database.dao.OrderDao
import com.trietng.coffeeapp.database.dao.UserDao
import com.trietng.coffeeapp.database.dao.VoucherDao
import com.trietng.coffeeapp.database.entity.Cart
import com.trietng.coffeeapp.database.entity.Coffee
import com.trietng.coffeeapp.database.entity.Loyalty
import com.trietng.coffeeapp.database.entity.Order
import com.trietng.coffeeapp.database.entity.User
import com.trietng.coffeeapp.database.entity.Voucher
import kotlinx.coroutines.runBlocking

// No database migration is needed since this is a university project
@Database(entities = [User::class, Coffee::class, Cart::class, Order::class, Loyalty::class, Voucher::class], version = 1, exportSchema = false)
abstract class CoffeeRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun coffeeDao(): CoffeeDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun loyaltyDao(): LoyaltyDao
    abstract fun voucherDao(): VoucherDao

    fun populateDatabase() = runBlocking {

        // Insert default user profile
        userDao().insert(
            User(0,
                "Dinh Triet",
                "0123456789",
                "triet@email.com",
                "227 Nguyen Van Cu Street, District 5, Ho Chi Minh City")
        )

        // coffeeDao and loyaltyDao can be optimized with a count check

        // Insert data to the coffee table
        coffeeDao().insert(Coffee(0, "Americano", 3.00, "coffee_0"))
        coffeeDao().insert(Coffee(1, "Cappuccino", 3.00, "coffee_1"))
        coffeeDao().insert(Coffee(2, "Mocha", 2.50, "coffee_2"))
        coffeeDao().insert(Coffee(3, "Flat White", 3.50, "coffee_3"))

        // Insert fake data to the loyalty table, each of them has unique time_added
        if (loyaltyDao().count() == 0) {
            loyaltyDao().insert(Loyalty(0,"Americano", 12, "2023-02-15 08:45:23"))
            loyaltyDao().insert(Loyalty(1,"Cappuccino", 15, "2023-01-10 13:20:30"))
            loyaltyDao().insert(Loyalty(2,"Espresso", 10, "2023-03-25 22:15:45"))
            loyaltyDao().insert(Loyalty(3,"Flat White", 12, "2023-02-15 08:45:23"))
            loyaltyDao().insert(Loyalty(4,"Cappuccino", 15, "2023-01-10 13:20:30"))
            loyaltyDao().insert(Loyalty(5,"Moca", 10, "2023-03-25 22:15:45"))
            loyaltyDao().insert(Loyalty(6,"Latte", 12, "2023-02-15 08:45:23"))
            loyaltyDao().insert(Loyalty(7,"Redeem", -25, "2023-01-10 13:20:30"))
        }

        // Insert fake data to the voucher table
        if (voucherDao().count() == 0) {
            voucherDao().insert(Voucher(0, 0, 1, "2024-02-15 00:00:00", 30))
            voucherDao().insert(Voucher(1, 1, 1, "2024-02-16 00:00:00", 35))
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CoffeeRoomDatabase? = null

        fun getDatabase(context: Context): CoffeeRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoffeeRoomDatabase::class.java,
                    "coffee_database"
                ).build() // Callback function does not work here!!!
                instance.populateDatabase()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

}
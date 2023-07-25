package com.trietng.coffeeapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trietng.coffeeapp.database.entity.Coffee
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {

    // On database creation insertion, ignore if there is a conflict
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coffee: Coffee)

    // Get all coffee
    @Query("select * from coffee")
    fun getAllCoffee(): Flow<List<Coffee>>

}
package com.trietng.coffeeapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trietng.coffeeapp.database.entity.Loyalty
import kotlinx.coroutines.flow.Flow

@Dao
interface LoyaltyDao {

    // On database creation insertion, ignore if there is a conflict
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(loyalty: Loyalty)

    // Count the number of entries in the loyalty table
    @Query("select count(*) from loyalty")
    suspend fun count(): Int

    // Add a new entry to the loyalty table
    @Query("insert into loyalty (content, point, time_added) values (:content, :point, :timeAdded)")
    suspend fun insert(content: String, point: Int, timeAdded: String)

    // Get 10 most recent entries from the loyalty table
    @Query("select * from loyalty order by time_added desc limit 10")
    fun getRecent(): Flow<List<Loyalty>>

    // Get sum of points from the loyalty table
    @Query("select sum(point) from loyalty")
    fun getSumPoint(): Flow<Int?>

    // Delete all entries from the loyalty table
    @Query("delete from loyalty")
    suspend fun deleteAll()
}
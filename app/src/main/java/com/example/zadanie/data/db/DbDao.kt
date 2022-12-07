package com.example.zadanie.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.FriendItem
import com.example.zadanie.data.db.model.UserItem

@Dao
interface DbDao {
    // BARS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBars(bars: List<BarItem>)

    @Query("DELETE FROM bars")
    suspend fun deleteBars()

    @Query("SELECT * FROM bars")
    fun getBars(): LiveData<List<BarItem>?>

    @Query("SELECT * FROM bars order by name ASC")
    fun getBarsSortAsc(): LiveData<List<BarItem>?>

    @Query("SELECT * FROM bars order by name DESC")
    fun getBarsSortDesc(): LiveData<List<BarItem>?>

    @Query("SELECT * FROM bars order by visitors DESC")
    fun getBarsSortVisitors(): LiveData<List<BarItem>?>

    // FRIENDS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(friends: List<FriendItem>)

    @Query("DELETE FROM friends")
    suspend fun deleteFriends()

    @Query("SELECT * FROM friends")
    fun getFriends(): LiveData<List<FriendItem>?>

    // USER
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserItem)

    @Query("SELECT * FROM user")
    fun getUser(): LiveData<UserItem>

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}
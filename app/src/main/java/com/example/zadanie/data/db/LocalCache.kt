package com.example.zadanie.data.db

import androidx.lifecycle.LiveData
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.FriendItem
import com.example.zadanie.data.db.model.UserItem
import com.example.zadanie.helpers.Sort

class LocalCache(private val dao: DbDao) {
    // BARS
    suspend fun insertBars(bars: List<BarItem>){
        dao.insertBars(bars)
    }

    suspend fun deleteBars(){ dao.deleteBars() }

    fun getBars(sort: Sort): LiveData<List<BarItem>?> {
        return when (sort) {
            Sort.NAME_ASC -> { dao.getBarsSortAsc() }
            Sort.NAME_DESC -> { dao.getBarsSortDesc() }
            Sort.VISITORS -> { dao.getBarsSortVisitors() }
        }
    }

    // FRIENDS
    suspend fun insertFriends(friends: List<FriendItem>){
        dao.insertFriends(friends)
    }

    suspend fun deleteFriends(){ dao.deleteFriends() }

    fun getFriends(): LiveData<List<FriendItem>?> {
        return dao.getFriends()
    }

    // USER
    suspend fun insertUser(user: UserItem){
        dao.insertUser(user)
    }

    fun getUser(): LiveData<UserItem> { return dao.getUser() }

    suspend fun deleteUser(){ dao.deleteUser() }
}
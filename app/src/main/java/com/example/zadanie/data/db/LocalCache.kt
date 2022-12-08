package com.example.zadanie.data.db

import androidx.lifecycle.LiveData
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.FriendItem
import com.example.zadanie.data.db.model.UserItem
import com.example.zadanie.helpers.Sort
import com.example.zadanie.helpers.SortMethod

class LocalCache(private val dao: DbDao) {
    // BARS
    suspend fun insertBars(bars: List<BarItem>){
        dao.insertBars(bars)
    }

    suspend fun deleteBars(){ dao.deleteBars() }

    fun getBars(sort: Sort): LiveData<List<BarItem>?> {
        return if (sort.asc) {
            when (sort.sortMethod) {
                SortMethod.NAME -> { dao.getBarsNameAsc() }
                SortMethod.DISTANCE -> { dao.getBarsVisitorsAsc() }
                SortMethod.VISITORS -> { dao.getBarsDistanceAsc() }
            }
        } else {
            when (sort.sortMethod) {
                SortMethod.NAME -> { dao.getBarsNameDesc() }
                SortMethod.DISTANCE -> { dao.getBarsVisitorsDesc() }
                SortMethod.VISITORS -> { dao.getBarsDistanceDesc() }
            }
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
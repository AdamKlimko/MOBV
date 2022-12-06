package com.example.zadanie.data.db

import androidx.lifecycle.LiveData
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.helpers.Sort

class LocalCache(private val dao: DbDao) {
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
}
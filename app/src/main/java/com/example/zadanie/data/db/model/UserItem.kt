package com.example.zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserItem(
    @PrimaryKey val uid: String,
    val name: String
)
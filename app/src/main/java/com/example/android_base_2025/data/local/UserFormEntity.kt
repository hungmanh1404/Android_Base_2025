package com.example.android_base_2025.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_forms")
data class UserFormEntity2(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val email: String,
    val comment: String,
    val address: String,
    val createdAt: Long = System.currentTimeMillis()
)



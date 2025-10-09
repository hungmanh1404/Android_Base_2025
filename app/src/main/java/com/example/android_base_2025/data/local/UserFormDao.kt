package com.example.android_base_2025.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFormDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(form: UserFormEntity): Long

    @Query("SELECT * FROM user_forms ORDER BY createdAt DESC")
    fun getAll(): Flow<List<UserFormEntity>>

    @Query("DELETE FROM user_forms WHERE id = :id")
    suspend fun deleteById(id: Long)
}

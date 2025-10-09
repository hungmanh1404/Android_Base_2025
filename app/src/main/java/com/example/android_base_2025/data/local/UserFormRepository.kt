package com.example.android_base_2025.data.local

import android.content.Context
import kotlinx.coroutines.flow.Flow

class UserFormRepository(context: Context) {
    private val dao: UserFormDao = AppDatabase.getInstance(context).userFormDao()

    fun getAll(): Flow<List<UserFormEntity>> = dao.getAll()

    suspend fun add(name: String, email: String, comment: String, address: String) {
        dao.insert(UserFormEntity(name = name, email = email, comment = comment, address = address))
    }

    suspend fun delete(id: Long) {
        dao.deleteById(id)
    }
}
package com.example.android_base_2025.data.local

import android.content.Context
import kotlinx.coroutines.flow.Flow

class NoteRepository(context: Context) {
    private val noteDao: NoteDao = AppDatabase.getInstance(context).noteDao()

    fun getAllNotes(): Flow<List<NoteEntity>> = noteDao.getAllNotes()

    suspend fun addNote(title: String, content: String) {
        noteDao.insert(NoteEntity(title = title, content = content))
    }

    suspend fun clear() {
        noteDao.clearAll()
    }
}

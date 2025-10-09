package com.example.android_base_2025.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

object AppPrefs {
    private const val PREF_NAME = "app_preferences"
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    // ----------- FLOW DATA KEYS -----------
    const val KEY_COMMENT = "flow_comment"
    const val KEY_FOLDER = "flow_folder"
    const val KEY_JOB = "flow_job"


    internal fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // ----------- BASIC TYPES -----------
    private fun setString(key: String, value: String?) = prefs.edit { putString(key, value) }
    private fun getString(key: String, defaultValue: String = ""): String =
        prefs.getString(key, defaultValue) ?: defaultValue

    private fun setInt(key: String, value: Int) = prefs.edit { putInt(key, value) }
    private fun getInt(key: String, defaultValue: Int = 0): Int = prefs.getInt(key, defaultValue)

    private fun setBoolean(key: String, value: Boolean) = prefs.edit { putBoolean(key, value) }
    private fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        prefs.getBoolean(key, defaultValue)

    private fun setFloat(key: String, value: Float) = prefs.edit { putFloat(key, value) }
    private fun getFloat(key: String, defaultValue: Float = 0f): Float = prefs.getFloat(key, defaultValue)

    private fun setLong(key: String, value: Long) = prefs.edit { putLong(key, value) }
    private fun getLong(key: String, defaultValue: Long = 0L): Long = prefs.getLong(key, defaultValue)

    // ----------- LIST -----------
    private fun <T> setList(key: String, list: List<T>) {
        val json = gson.toJson(list)
        setString(key, json)
    }

    private inline fun <reified T> getList(key: String): List<T> {
        val json = getString(key)
        if (json.isEmpty()) return emptyList()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(json, type)
    }

    // ----------- OBJECT -----------
    private fun <T> setObject(key: String, obj: T) {
        val json = gson.toJson(obj)
        setString(key, json)
    }

    private inline fun <reified T> getObject(key: String): T? {
        val json = getString(key)
        if (json.isEmpty()) return null
        return gson.fromJson(json, T::class.java)
    }
    
    /**
     * Lưu dữ liệu flow với key tùy chỉnh
     */
    internal fun saveFlowData(key: String, value: String) = setString(key, value)
    internal fun getFlowData(key: String): String = getString(key)
    
    /**
     * Xóa tất cả dữ liệu flow
     */
    internal fun clearFlowData() {
        clearKey(KEY_COMMENT)
        clearKey(KEY_FOLDER)
        clearKey(KEY_JOB)
    }
    
    /**
     * Kiểm tra có dữ liệu flow nào không
     */
    internal fun hasFlowData(): Boolean {
        return getFlowData(KEY_COMMENT).isNotEmpty() || 
               getFlowData(KEY_FOLDER).isNotEmpty() || 
               getFlowData(KEY_JOB).isNotEmpty()
    }

    // ----------- CLEAR -----------
    private fun clearKey(key: String) = prefs.edit { remove(key) }
}

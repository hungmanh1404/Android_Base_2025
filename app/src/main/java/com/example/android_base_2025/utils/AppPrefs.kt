package com.example.android_base_2025.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AppPrefs {

    private const val PREF_NAME = "app_preferences"
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // ----------- BASIC TYPES -----------
    fun setString(key: String, value: String?) = prefs.edit().putString(key, value).apply()
    fun getString(key: String, defaultValue: String = ""): String =
        prefs.getString(key, defaultValue) ?: defaultValue

    fun setInt(key: String, value: Int) = prefs.edit().putInt(key, value).apply()
    fun getInt(key: String, defaultValue: Int = 0): Int = prefs.getInt(key, defaultValue)

    fun setBoolean(key: String, value: Boolean) = prefs.edit().putBoolean(key, value).apply()
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        prefs.getBoolean(key, defaultValue)

    fun setFloat(key: String, value: Float) = prefs.edit().putFloat(key, value).apply()
    fun getFloat(key: String, defaultValue: Float = 0f): Float = prefs.getFloat(key, defaultValue)

    fun setLong(key: String, value: Long) = prefs.edit().putLong(key, value).apply()
    fun getLong(key: String, defaultValue: Long = 0L): Long = prefs.getLong(key, defaultValue)

    // ----------- LIST -----------
    fun <T> setList(key: String, list: List<T>) {
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
    fun <T> setObject(key: String, obj: T) {
        val json = gson.toJson(obj)
        setString(key, json)
    }

    private inline fun <reified T> getObject(key: String): T? {
        val json = getString(key)
        if (json.isEmpty()) return null
        return gson.fromJson(json, T::class.java)
    }

    // ----------- CLEAR -----------
    fun clearKey(key: String) = prefs.edit().remove(key).apply()
    fun clearAll() = prefs.edit().clear().apply()
}

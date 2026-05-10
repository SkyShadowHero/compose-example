package com.skyshadow.example.presentation.util

import android.content.Context
import com.skyshadow.example.MainApplication

actual object ThemeStorage {
    private val prefs by lazy {
        MainApplication.context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    }

    actual fun loadFollowSystem(): Boolean = prefs.getBoolean("follow_system", true)
    actual fun saveFollowSystem(value: Boolean) = prefs.edit().putBoolean("follow_system", value).apply()
    actual fun loadDarkMode(): Boolean = prefs.getBoolean("dark_mode", false)
    actual fun saveDarkMode(value: Boolean) = prefs.edit().putBoolean("dark_mode", value).apply()
}

package com.skyshadow.example.presentation.util

import java.util.prefs.Preferences

actual object ThemeStorage {
    private val prefs = Preferences.userNodeForPackage(ThemeStorage::class.java)

    actual fun loadFollowSystem(): Boolean = prefs.getBoolean("follow_system", true)
    actual fun saveFollowSystem(value: Boolean) { prefs.putBoolean("follow_system", value) }
    actual fun loadDarkMode(): Boolean = prefs.getBoolean("dark_mode", false)
    actual fun saveDarkMode(value: Boolean) { prefs.putBoolean("dark_mode", value) }
}

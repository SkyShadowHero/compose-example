package com.skyshadow.example.presentation.util

import platform.Foundation.NSUserDefaults

actual object ThemeStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    actual fun loadFollowSystem(): Boolean =
        defaults.objectForKey("follow_system")?.let { defaults.boolForKey("follow_system") } ?: true
    actual fun saveFollowSystem(value: Boolean) { defaults.setBool(value, "follow_system") }
    actual fun loadDarkMode(): Boolean =
        defaults.objectForKey("dark_mode")?.let { defaults.boolForKey("dark_mode") } ?: false
    actual fun saveDarkMode(value: Boolean) { defaults.setBool(value, "dark_mode") }
}

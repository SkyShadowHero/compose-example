package com.skyshadow.example.presentation.util

/** 持久化布尔值存储 */
expect object ThemeStorage {
    fun loadFollowSystem(): Boolean
    fun saveFollowSystem(value: Boolean)
    fun loadDarkMode(): Boolean
    fun saveDarkMode(value: Boolean)
}

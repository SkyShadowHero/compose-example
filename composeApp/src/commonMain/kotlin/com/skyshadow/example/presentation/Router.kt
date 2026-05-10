package com.skyshadow.example.presentation

/**
 * 一级标签页路由。
 */
sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "首页")
    data object Profile : Screen("profile", "我的")
    data object Settings : Screen("settings", "设置")

    companion object {
        val tabs = listOf(Home, Profile, Settings)
        fun fromRoute(route: String): Screen =
            tabs.find { it.route == route } ?: Home
    }
}

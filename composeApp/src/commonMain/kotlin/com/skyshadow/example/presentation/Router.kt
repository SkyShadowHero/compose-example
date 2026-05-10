package com.skyshadow.example.presentation

/**
 * 一级标签页路由。
 */
sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "主页")
    data object Profile : Screen("profile", "信息")

    companion object {
        val tabs = listOf(Home, Profile)
        fun fromRoute(route: String): Screen =
            tabs.find { it.route == route } ?: Home
    }
}

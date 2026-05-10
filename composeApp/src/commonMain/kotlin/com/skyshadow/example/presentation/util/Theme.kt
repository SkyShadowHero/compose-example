package com.skyshadow.example.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import top.yukonga.miuix.kmp.theme.ColorSchemeMode
import top.yukonga.miuix.kmp.theme.ThemeController

/**
 * 主题状态：跟随系统 / 手动亮暗。
 */
class ThemeState(
    initialFollowSystem: Boolean = true,
    initialIsDark: Boolean = false,
) {
    var followSystem by mutableStateOf(initialFollowSystem)
    var isDark by mutableStateOf(initialIsDark)

    val colorMode: ColorSchemeMode
        get() = when {
            followSystem -> ColorSchemeMode.System
            isDark -> ColorSchemeMode.Dark
            else -> ColorSchemeMode.Light
        }

    val controller: ThemeController
        get() = ThemeController(colorMode)
}

@Composable
fun rememberThemeState(): ThemeState {
    return remember { ThemeState() }
}

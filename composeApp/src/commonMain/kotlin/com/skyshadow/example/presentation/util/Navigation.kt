package com.skyshadow.example.presentation.util

import androidx.compose.runtime.Composable

/**
 * 导航页面标记，用于 AnimatedContent 状态切换。
 */
sealed class NavPage {
    data object Tabs : NavPage()
    data object Settings : NavPage()
    data object About : NavPage()
}

/**
 * 跨平台返回拦截（Android 上使用系统 BackHandler，
 * 其他平台为空操作）。支持预测性返回手势。
 */
@Composable
expect fun BackHandler(
    enabled: Boolean = true,
    onBack: () -> Unit,
)

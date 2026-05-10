package com.skyshadow.example.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.FloatingNavigationBar
import top.yukonga.miuix.kmp.basic.FloatingNavigationBarItem
import top.yukonga.miuix.kmp.blur.Backdrop
import top.yukonga.miuix.kmp.blur.blur
import top.yukonga.miuix.kmp.blur.drawBackdrop
import top.yukonga.miuix.kmp.blur.highlight.Highlight
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.extended.Contacts
import top.yukonga.miuix.kmp.icon.extended.Settings
import top.yukonga.miuix.kmp.icon.extended.VerticalSplit
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * Miuix 悬浮底部导航栏组件。
 *
 * 状态由外部管理，通过 [selectedIndex] 和 [onTabChange] 控制。
 */
@Composable
fun MiuixBottomBar(
    backdrop: Backdrop,
    selectedIndex: Int,
    onTabChange: (Int) -> Unit,
) {
    val items = listOf("首页", "我的", "设置")
    val icons = listOf(MiuixIcons.VerticalSplit, MiuixIcons.Contacts, MiuixIcons.Settings)

    FloatingNavigationBar(
        color = MiuixTheme.colorScheme.surface.copy(alpha = 0.2f),
        modifier = Modifier.drawBackdrop(
            backdrop = backdrop,
            shape = { RoundedCornerShape(32.dp) },
            effects = { blur(12.dp.toPx()) },
            highlight = { Highlight.GlassStrokeMiddleLight },
        ),
    ) {
        items.forEachIndexed { index, label ->
            FloatingNavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onTabChange(index) },
                icon = icons[index],
                label = label,
            )
        }
    }
}

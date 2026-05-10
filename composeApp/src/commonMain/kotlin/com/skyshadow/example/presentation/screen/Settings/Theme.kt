package com.skyshadow.example.presentation.screen.Settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skyshadow.example.presentation.component.RefreshBox
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.preference.OverlayDropdownPreference
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * 主题设置二级页面。
 */
@Composable
fun ThemeScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    followSystem: Boolean = true,
    onFollowSystemChange: (Boolean) -> Unit = {},
    isDarkMode: Boolean = false,
    onToggleDarkMode: (Boolean) -> Unit = {},
) {
    val modeOptions = listOf("亮色", "深色")

    RefreshBox(contentPadding = contentPadding) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
            item {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = MiuixTheme.colorScheme.surfaceContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                        .padding(horizontal = 16.dp),
                ) {
                    Column {
                        // 跟随系统
                        Surface(
                            onClick = { onFollowSystemChange(!followSystem) },
                            color = MiuixTheme.colorScheme.surfaceContainer,
                        ) {
                            androidx.compose.foundation.layout.Row(
                                modifier = Modifier
                                    .fillMaxWidth()

                                    .padding(start = 16.dp, end = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "跟随系统",
                                    style = MiuixTheme.textStyles.body1,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 18.dp),
                                )
                                Switch(
                                    checked = followSystem,
                                    onCheckedChange = onFollowSystemChange,
                                )
                            }
                        }

                        // 手动选择亮/暗
                        if (!followSystem) {
                            OverlayDropdownPreference(
                                title = "主题模式",
                                items = modeOptions,
                                selectedIndex = if (isDarkMode) 1 else 0,
                                onSelectedIndexChange = { onToggleDarkMode(it == 1) },
                            )
                        }
                    }
                }
            }
        }
    }
}

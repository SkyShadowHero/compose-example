package com.skyshadow.example.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.LocalIndication
import androidx.compose.material3.ripple
import com.skyshadow.example.presentation.component.RefreshBox
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.PressFeedbackType
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.preference.ArrowPreference

/**
 * 信息页面（Tab 2）。
 *
 * 顶部为可点击卡片（按压下沉效果），底部为设置 / 关于选项分组。
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onNavigateToSettings: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
) {
    RefreshBox(contentPadding = contentPadding) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            // 顶部间距
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
            item {
                BasicComponent(
                    title = "信息",
                    summary = "Miuix"
                )
            }
            // 可点击卡片 — 按压下沉效果
            item {
                Card(
                    cornerRadius = 24.dp,
                    colors = CardDefaults.defaultColors(),
                    pressFeedbackType = PressFeedbackType.Sink,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                ) {
                    androidx.compose.foundation.layout.Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "这是天影大侠的miuix测试应用",
                            modifier = Modifier.padding(bottom = 4.dp, top = 10.dp),
                        )
                        Text(
                            text = "基于 Compose Multiplatform + Miuix 组件库，感谢DeepSeek",
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            modifier = Modifier.padding(bottom = 10.dp),
                        )
                    }
                }
            }

            // 选项分组
            item {
                CompositionLocalProvider(LocalIndication provides ripple()) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = MiuixTheme.colorScheme.surfaceContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                    ) {
                        androidx.compose.foundation.layout.Column {
                            ArrowPreference(
                                title = "设置",
                                onClick = onNavigateToSettings,
                            )
                            ArrowPreference(
                                title = "关于",
                                onClick = onNavigateToAbout,
                            )
                        }
                    }
                }
            }
        }
    }
}

package com.skyshadow.example.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skyshadow.example.presentation.component.GreetingCard
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * 首页 —— 展示 Miuix 组件和 Compose Multiplatform 基础用法。
 */
@Composable
fun HomeScreen() {
    MiuixTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item {
                    SmallTitle(text = "开始学习 Compose + Miuix ✨")
                }
                item {
                    BasicComponent(
                        title = "Miuix 组件库",
                        summary = "HyperOS 设计风格，跨平台组件"
                    )
                }
                item {
                    BasicComponent(
                        title = "Compose Multiplatform",
                        summary = "跨平台 UI 框架，一次编写多端运行"
                    )
                }
                item {
                    GreetingCard(
                        title = "自定义组件",
                        summary = "这是 presentation/component 中的复用组件"
                    )
                }
                item {
                    Text(
                        text = "祝你学习愉快！",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }
        }
    }
}

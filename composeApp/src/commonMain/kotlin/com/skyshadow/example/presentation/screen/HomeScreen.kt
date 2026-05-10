package com.skyshadow.example.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skyshadow.example.presentation.component.MiuixCard
import com.skyshadow.example.presentation.component.RefreshBox
import top.yukonga.miuix.kmp.basic.BasicComponent

/**
 * 首页内容 —— 由 App.kt 的 Scaffold 提供外层布局。
 * 下拉刷新由 RefreshBox 组件处理。
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    RefreshBox(contentPadding = contentPadding) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
            item {
                BasicComponent(
                    title = "主页",
                    summary = "Miuix"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是一段很长很长的卡片描述"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }
            item {
                MiuixCard(
                    title = "卡片",
                    summary = "我是卡片"
                )
            }

        }
    }
}

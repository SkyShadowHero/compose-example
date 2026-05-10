package com.skyshadow.example.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Card

/**
 * 一个简单的 Miuix Card 组件示例。
 * 展示如何在页面中复用的通用组件。
 */
@Composable
fun GreetingCard(
    title: String,
    summary: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp)
        ) {
            top.yukonga.miuix.kmp.basic.Text(
                text = title,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            top.yukonga.miuix.kmp.basic.Text(
                text = summary
            )
        }
    }
}

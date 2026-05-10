package com.skyshadow.example.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skyshadow.example.presentation.component.RefreshBox
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.preference.ArrowPreference
import top.yukonga.miuix.kmp.theme.MiuixTheme
/**
 * 设置页面。
 */
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onNavigateToTheme: () -> Unit = {},
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
                    title = "设置",
                    summary = "Miuix"
                )
            }
            item {
                CompositionLocalProvider(LocalIndication provides ripple()) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = MiuixTheme.colorScheme.surfaceContainer,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        androidx.compose.foundation.layout.Column {
                            ArrowPreference(title = "主题", onClick = onNavigateToTheme)
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                        }
                    }
                }
            }
            item {
                CompositionLocalProvider(LocalIndication provides ripple()) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = MiuixTheme.colorScheme.surfaceContainer,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        androidx.compose.foundation.layout.Column {
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                            ArrowPreference(title = "设置", onClick = { /* TODO */ })
                        }
                    }
                }
            }
        }
    }
}
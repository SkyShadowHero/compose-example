package com.skyshadow.example.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.rememberPullToRefreshState
import kotlin.time.Duration.Companion.milliseconds
import androidx.compose.ui.unit.dp
/**
 * 可复用的下拉刷新容器。
 *
 * 内部管理刷新状态，刷新时模拟 1.5 秒延迟后自动结束。
 *
 * @param refreshTexts 四个状态对应的提示文字：下拉中 / 达到阈值 / 刷新中 / 完成
 * @param modifier     修饰符
 * @param content      可滚动内容
 */
@Composable
fun RefreshBox(
    refreshTexts: List<String> = listOf(
        "下拉刷新",
        "松手为您刷新",
        "正在刷新",
        "刷新成功再见朋友"
    ),
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit,
) {
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(300.milliseconds)
            isRefreshing = false
        }
    }

    PullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { isRefreshing = true },
        pullToRefreshState = pullToRefreshState,
        refreshTexts = refreshTexts,
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        content()
    }
}

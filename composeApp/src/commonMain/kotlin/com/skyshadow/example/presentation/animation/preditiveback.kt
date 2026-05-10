package com.skyshadow.example.presentation.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 完整页面过渡动画（顶栏 + 底栏 + 内容一起滑动）。
 *
 * 正向：整页从右侧滑入。
 * 返回：整页从右侧滑出。
 */
@Composable
fun <T> PredictiveBackTransition(
    targetState: T,
    isBack: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedContentScope.(T) -> Unit,
) {
    val transitionSpec: AnimatedContentTransitionScope<T>.() -> ContentTransform = {
        if (isBack) {
            ContentTransform(
                targetContentEnter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(350),
                ),
                initialContentExit = slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(350),
                ),
                sizeTransform = null,
            )
        } else {
            ContentTransform(
                targetContentEnter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(350),
                ),
                initialContentExit = slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(350),
                ),
                sizeTransform = null,
            )
        }
    }

    AnimatedContent(
        targetState = targetState,
        transitionSpec = transitionSpec,
        label = "predictiveBack",
        modifier = modifier,
        content = content,
    )
}

package com.skyshadow.example.presentation.component

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.SmallTopAppBar
import top.yukonga.miuix.kmp.blur.Backdrop
import top.yukonga.miuix.kmp.blur.textureBlur
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.extended.Back
import top.yukonga.miuix.kmp.utils.TiltFeedback
import top.yukonga.miuix.kmp.utils.pressable

/**
 * Miuix 顶部应用栏组件。
 * 仅在 [onBack] 非空时显示返回按钮，带倾斜按压反馈。
 */
@Composable
fun MiuixTopBar(
    title: String = "Compose Example",
    onBack: (() -> Unit)? = null,
    backdrop: Backdrop,
) {
    SmallTopAppBar(
        modifier = Modifier.textureBlur(
            backdrop = backdrop,
            shape = RoundedCornerShape(16.dp),
        ),
        title = title,
        color = Color.Transparent,
        navigationIcon = {
            if (onBack != null) {
                val interactionSource = remember { MutableInteractionSource() }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .pressable(
                            interactionSource = interactionSource,
                            indication = TiltFeedback(),
                        )
                        .combinedClickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onBack,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = MiuixIcons.Back,
                        contentDescription = "返回",
                    )
                }
            }
        },
    )
}

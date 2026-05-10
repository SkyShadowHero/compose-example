package com.skyshadow.example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.saveable.rememberSaveable
import com.skyshadow.example.presentation.Screen
import com.skyshadow.example.presentation.animation.PredictiveBackTransition
import com.skyshadow.example.presentation.util.BackHandler
import com.skyshadow.example.presentation.util.ThemeStorage
import com.skyshadow.example.presentation.util.NavPage
import com.skyshadow.example.presentation.component.MiuixBottomBar
import com.skyshadow.example.presentation.component.MiuixTopBar
import com.skyshadow.example.presentation.screen.HomeScreen
import com.skyshadow.example.presentation.screen.ProfileScreen
import com.skyshadow.example.presentation.screen.SettingsScreen
import com.skyshadow.example.presentation.screen.Settings.ThemeScreen
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.blur.layerBackdrop
import top.yukonga.miuix.kmp.blur.rememberLayerBackdrop
import top.yukonga.miuix.kmp.theme.ColorSchemeMode
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.ThemeController

@Composable
fun App() {
    var followSystem by remember { mutableStateOf(ThemeStorage.loadFollowSystem()) }
    var isDarkMode by remember { mutableStateOf(ThemeStorage.loadDarkMode()) }
    val colorMode = if (followSystem) ColorSchemeMode.System
        else if (isDarkMode) ColorSchemeMode.Dark
        else ColorSchemeMode.Light

    MiuixTheme(controller = remember(colorMode) { ThemeController(colorMode) }) {
        var currentPage by remember { mutableStateOf<NavPage>(NavPage.Tabs) }
        var isBack by remember { mutableStateOf(false) }

        fun navigateTo(page: NavPage) {
            isBack = page is NavPage.Tabs
            currentPage = page
        }

        BackHandler(enabled = currentPage !is NavPage.Tabs) {
            navigateTo(NavPage.Tabs)
        }
        val pagerState = rememberPagerState(pageCount = { Screen.tabs.size })
        val coroutineScope = rememberCoroutineScope()

        PredictiveBackTransition(
            targetState = currentPage,
            isBack = isBack,
        ) { page ->
            when (page) {
                NavPage.Tabs -> {
                    val bg = MiuixTheme.colorScheme.background
                    val tabsBackdrop = rememberLayerBackdrop {
                        drawRect(bg)
                        drawContent()
                    }
                    Scaffold(
                        topBar = {
                            MiuixTopBar(
                                backdrop = tabsBackdrop,
                                title = Screen.tabs[pagerState.currentPage].title,
                            )
                        },
                        bottomBar = {
                            MiuixBottomBar(
                                backdrop = tabsBackdrop,
                                selectedIndex = pagerState.currentPage,
                                onTabChange = { coroutineScope.launch { pagerState.animateScrollToPage(it) } },
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .layerBackdrop(tabsBackdrop)
                        ) {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxSize(),
                            ) { pagerPage ->
                                when (Screen.tabs[pagerPage]) {
                                    Screen.Home -> HomeScreen(
                                        modifier = Modifier.fillMaxSize(),
                                        contentPadding = paddingValues,
                                    )

                                    Screen.Profile -> ProfileScreen(
                                        modifier = Modifier.fillMaxSize(),
                                        contentPadding = paddingValues,
                                    )

                                    Screen.Settings -> SettingsScreen(
                                        modifier = Modifier.fillMaxSize(),
                                        contentPadding = paddingValues,
                                        onNavigateToTheme = { navigateTo(NavPage.Theme) },
                                    )
                                }
                            }
                        }
                    }
                }

                NavPage.Theme -> {
                    val bg = MiuixTheme.colorScheme.background
                    val themeBackdrop = rememberLayerBackdrop {
                        drawRect(bg)
                        drawContent()
                    }
                    Scaffold(
                        topBar = {
                            MiuixTopBar(
                                backdrop = themeBackdrop,
                                title = "主题",
                                onBack = { navigateTo(NavPage.Tabs) },
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .layerBackdrop(themeBackdrop)
                        ) {
                            ThemeScreen(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = paddingValues,
                                followSystem = followSystem,
                                onFollowSystemChange = {
                                    followSystem = it
                                    ThemeStorage.saveFollowSystem(it)
                                },
                                isDarkMode = isDarkMode,
                                onToggleDarkMode = {
                                    isDarkMode = it
                                    ThemeStorage.saveDarkMode(it)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

# Agent Context — compose-example

## Project Overview

基于 **Compose Multiplatform + Miuix** 的跨平台学习项目。通过实际代码演示 Miuix 组件库的用法，覆盖 **Android / iOS / Desktop (JVM)** 三端。

**项目名（settings）**: `Dynamicyouthkmp`

## Tech Stack

| Layer | Choice |
|---|---|
| UI Framework | Compose Multiplatform 1.10.3 |
| Component Library | Miuix 0.9.0-54554285-SNAPSHOT (HyperOS design) |
| Language | Kotlin 2.3.20 |
| Build System | Gradle (Kotlin DSL), AGP 8.11.2 |
| Navigation | sealed class `NavPage` + `PredictiveBackTransition`（基于 `AnimatedContent`，非 Navigation Compose NavHost） |
| Persistence | Android: SharedPreferences / Desktop: java.util.prefs / iOS: NSUserDefaults (通过 expect/actual) |
| CI/CD | GitLab CI (`compileDebugKotlinAndroid` + `assembleDebug`) |
| Min / Target / Compile SDK | Android 32 / 36 / 37 |

## Miuix Dependency Modules (`gradle/libs.versions.toml`)

| Module | Description |
|---|---|
| `miuix-ui` | 核心 UI 组件（Scaffold、Card、Button、Text 等） |
| `miuix-preference` | Preference 组件（ArrowPreference、SwitchPreference 等） |
| `miuix-icons` | 扩展图标集合（MiuixIcons.*） |
| `miuix-navigation3-ui` | Navigation3 底层支持 |
| `miuix-blur` | 实时模糊与毛玻璃效果 |
| `miuix-shapes` | 形状工具库（声明但未在源码中直接使用） |

所有 Miuix 模块使用同一个 SNAPSHOT 版本号 `0.9.0-54554285-SNAPSHOT`。

## Directory Structure

```
composeApp/src/
├── commonMain/kotlin/com/skyshadow/example/
│   ├── App.kt                              # 应用入口 + MiuixTheme + 路由 + Scaffold
│   └── presentation/
│       ├── Router.kt                        # Screen 密封类（route 定义）
│       ├── animation/
│       │   ├── PredictiveBackGesture.kt     # predictiveBackTransform Modifier
│       │   └── preditiveback.kt             # PredictiveBackTransition (AnimatedContent)
│       ├── component/
│       │   ├── MiuixTopBar.kt               # SmallTopAppBar + textureBlur 封装
│       │   ├── MiuixBottomBar.kt            # FloatingNavigationBar + blur + GlassStroke 封装
│       │   ├── MiuixCard.kt                 # Card + Tilt 按压反馈封装
│       │   └── RefreshBox.kt                # PullToRefresh 容器封装
│       ├── screen/
│       │   ├── HomeScreen.kt                # 主页（卡片列表 + 下拉刷新）
│       │   ├── ProfileScreen.kt             # 信息页（卡片 + ArrowPreference 列表）
│       │   ├── Settings/
│       │   │   └── SettingsScreen.kt        # 设置二级页（主题内容 + 小标题）
│       │   └── About/
│       │       └── AboutScreen.kt           # 关于二级页（空）
│       └── util/
│           ├── Navigation.kt                # NavPage 密封类 + expect 声明
│           ├── Storage.kt                   # ThemeStorage expect object
│           └── Theme.kt                     # ThemeState 状态管理
├── androidMain/kotlin/com/skyshadow/example/
│   ├── MainActivity.kt                      # ComponentActivity + setContent { App() }
│   ├── MainApplication.kt                   # Application + static context
│   └── presentation/util/
│       ├── Navigation.kt                    # actual: Android BackHandler
│       └── Storage.kt                       # actual: SharedPreferences
├── iosMain/kotlin/com/skyshadow/example/
│   └── MainViewController.kt                # ComposeUIViewController { App() }
├── nativeMain/kotlin/com/skyshadow/example/
│   └── presentation/util/
│       ├── Navigation.kt                    # actual: no-op (iOS)
│       └── Storage.kt                       # actual: NSUserDefaults
└── desktopMain/kotlin/com/skyshadow/example/
    ├── Main.kt                              # application { Window { App() } }
    └── presentation/util/
        ├── Navigation.kt                    # actual: no-op
        └── Storage.kt                       # actual: java.util.prefs
```

## Architecture & Navigation

### Route Model

```kotlin
sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "主页")
    data object Profile : Screen("profile", "信息")
    companion object { val tabs = listOf(Home, Profile) }
}
```

### Navigation State

```kotlin
sealed class NavPage {
    data object Tabs : NavPage()      // 主页 Tab 切换（Home + Profile）
    data object Settings : NavPage()  // 设置二级页
    data object About : NavPage()     // 关于二级页
}
```

### Page Transition

`PredictiveBackTransition` 基于 `AnimatedContent`，动画规格：

| Direction | Enter | Exit | Duration |
|---|---|---|---|
| Forward | slideIn (从右) | slideOut (向左) | 350ms tween |
| Back | slideIn (从左) | slideOut (向右) | 350ms tween |

Tab 切换使用 `HorizontalPager`（foundation pager），非动画切换。

### Page Hierarchy

```
App.kt
├── NavPage.Tabs ────────────────────────────────── 默认
│   └── HorizontalPager
│       ├── Screen.Home → HomeScreen.kt           主页
│       └── Screen.Profile → ProfileScreen.kt     信息
│            ├── Card（按压反馈）→ navigateTo(Settings)
│            └── ArrowPreference 分组
│                 ├── 设置 → navigateTo(Settings)
│                 └── 关于 → navigateTo(About)
├── NavPage.Settings → SettingsScreen.kt           设置二级页（有返回按钮）
│   ├── SmallTitle "主题设置"
│   ├── Switch "跟随系统"
│   └── OverlayDropdownPreference "主题模式"（亮色/深色）
└── NavPage.About → AboutScreen.kt                 关于二级页（有返回按钮，空内容）
```

### Back Handling

- `NavPage.Tabs` 状态下禁用 BackHandler（已在首页）
- Settings / About 下按返回 → `navigateTo(NavPage.Tabs)`
- Android: 使用 `androidx.activity.compose.BackHandler`
- iOS / Desktop: no-op actual

### Predictive Back Gesture (Android 34+)

`predictiveBackTransform` Modifier 在返回过程中：
- 页面缩小到 85%
- 右移 40%
- 透明度降到 50%

## Custom Components (`presentation/component/`)

### MiuixTopBar

| Prop | Type | Description |
|---|---|---|
| title | String | 标题文字 |
| onBack | (() -> Unit)? | 非 null 时显示返回按钮 |
| backdrop | Backdrop | 毛玻璃模糊背景 |

返回按钮：圆形 `combinedClickable` + `TiltFeedback` + `MiuixIcons.Back`。

### MiuixBottomBar

| Prop | Type | Description |
|---|---|---|
| backdrop | Backdrop | 毛玻璃背景 |
| selectedIndex | Int | 当前选中 Tab（0 = 主页, 1 = 信息） |
| onTabChange | (Int) -> Unit | Tab 切换回调 |

使用 `FloatingNavigationBar` + `blur(12.dp)` + `Highlight.GlassStrokeMiddleLight`，两个 Tab（主页 / 信息），图标为 `MiuixIcons.VerticalSplit` / `MiuixIcons.Contacts`。

### MiuixCard

| Prop | Type | Description |
|---|---|---|
| title | String | 卡片标题 |
| summary | String | 卡片摘要 |

基于 `Card` + `PressFeedbackType.Tilt`，水平 16dp 内边距 6dp 垂直间距。

### RefreshBox

| Prop | Type | Description |
|---|---|---|
| refreshTexts | List<String> | 4 个状态提示文字（默认：下拉中/松手刷新/正在刷新/完成） |
| contentPadding | PaddingValues | 传递给 PullToRefresh |
| content | @Composable | 可滚动内容 |

内部管理布尔 `isRefreshing` 状态，模拟 1.5 秒延迟后自动结束。

### Patterns to Follow

1. **Backdrop pattern**: 在页面级 `content` lambda 内创建 `rememberLayerBackdrop`，同时传递给 Scaffold 的 topBar/bottomBar 和内容层的 `.layerBackdrop()`，实现一致的毛玻璃效果。
2. **Top bar + back**: `onBack` 为 nullable — 非空才显示返回按钮。
3. **Press feedback**: `PressFeedbackType.Tilt` / `TiltFeedback` 用于 Miuix 风格按压反馈。
4. **Pull-to-refresh**: 用 `RefreshBox` 包裹 `LazyColumn`，可传自定义文案。
5. **Settings 分组**: 用 `Surface(shape = RoundedCornerShape(24.dp))` 包裹，内部放多个 `ArrowPreference`。
6. **Android Application**: `MainApplication` 持有静态 `context` 引用供 SharedPreferences 使用。
7. **Clickable Card**: 带 `onClick` 和 `pressFeedbackType = PressFeedbackType.Tilt` 的可点击卡片，视觉下沉效果。

## Theme System

```kotlin
// App.kt
val colorMode = if (followSystem) ColorSchemeMode.System
    else if (isDarkMode) ColorSchemeMode.Dark
    else ColorSchemeMode.Light

MiuixTheme(controller = remember(colorMode) { ThemeController(colorMode) }) { ... }
```

### ThemeState (`util/Theme.kt`)

```kotlin
class ThemeState(
    initialFollowSystem: Boolean = true,
    initialIsDark: Boolean = false,
) {
    var followSystem by mutableStateOf(initialFollowSystem)
    var isDark by mutableStateOf(initialIsDark)
    val colorMode: ColorSchemeMode get() = ...
    val controller: ThemeController get() = ThemeController(colorMode)
}
```

### Persistence (expect/actual)

| Platform | Backend | Key |
|---|---|---|
| Android | `SharedPreferences("theme_prefs")` | `follow_system`, `dark_mode` |
| Desktop | `java.util.prefs.Preferences` | `follow_system`, `dark_mode` |
| iOS / native | `NSUserDefaults.standardUserDefaults` | `follow_system`, `dark_mode` |

## Platform Entry Points

| Platform | File | Entry |
|---|---|---|
| Android | `MainActivity.kt` | `ComponentActivity` + `setContent { App() }` |
| Android | `MainApplication.kt` | `Application` + 静态 `context` |
| Desktop | `Main.kt` | `fun main() = application { Window { App() } }` |
| iOS | `MainViewController.kt` | `fun MainViewController() = ComposeUIViewController { App() }` |

## Miuix Components Used Across Screens

| Component | Home | Profile | Settings | About |
|---|---|---|---|---|
| Scaffold | ✓ | - | ✓ | ✓ |
| SmallTopAppBar | ✓(via TopBar) | - | ✓(via TopBar) | ✓(via TopBar) |
| FloatingNavigationBar | ✓(via BottomBar) | - | - | - |
| Card | ✓(via MiuixCard) | ✓(clickable, Tilt) | - | - |
| BasicComponent | ✓ | - | - | - |
| ArrowPreference | - | ✓ | - | - |
| PullToRefresh | ✓(via RefreshBox) | ✓(via RefreshBox) | ✓(via RefreshBox) | - |
| Surface | - | ✓ | ✓ | - |
| SmallTitle | - | - | ✓ | - |
| Switch | - | - | ✓ | - |
| OverlayDropdownPreference | - | - | ✓ | - |
| Text | ✓ | ✓ | ✓ | ✓ |

## Build & CI Details

### Gradle

| | Value |
|---|---|
| Root name | `Dynamicyouthkmp` |
| Gradle wrapper | ✓ (8.14.3 / 9.4.x caches present) |
| Configuration cache | Enabled |
| KMP targets | `androidTarget`, `iosArm64`, `iosSimulatorArm64`, `jvm("desktop")` |
| Desktop JVM args | `-Dskiko.scaleFactor=1.5 -Dskiko.renderApi=OPENGL` |
| Desktop main class | `com.skyshadow.example.MainKt` |
| Desktop module | `compose.desktop.currentOs` |

### Environment Variables

| Env Var | Effect |
|---|---|
| `USE_DOMESTIC_MIRROR=true` | 使用阿里云 Maven 镜像（Gradle 仓库 + 依赖仓库） |
| `GRADLE_OPTS` | CI 中禁用 daemon，启用缓存和并行 |

### GitLab CI (`.gitlab-ci.yml`)

| Stage | Job | Trigger | Command |
|---|---|---|---|
| verify | `compile-check` | MR events + main branch | `compileDebugKotlinAndroid` |
| build | `build-android-debug` | main + commit message 含 `[test-build]` | `assembleDebug` (产生 APK artifacts) |

Runner tag: `maven`。使用 `ghcr.io/redmadrobot/android/android-sdk:base` 镜像。

### `.gitignore` 要点

```
.kotlin, .gradle, .deepseek, **/build/, local.properties, .idea, .DS_Store
*.xcodeproj/*  (但是保留 project.pbxproj / xcshareddata / xcworkspace)
```

## Code Style Conventions

- **Package**: `com.skyshadow.example`
- **Imports**: Explicit（无 `*` wildcard 导入）
- **Composables**: 使用 `@Composable` + 默认参数（`Modifier = Modifier`）
- **State**: `mutableStateOf` / `rememberSaveable` / `by` 委托
- **Modifier order**: size → clip/padding → background/border → clickable
- **Miuix imports**: 全部 `top.yukonga.miuix.kmp.*`（**不要混淆** 为 `androidx.compose.material3.*`）
- **String resources**: 目前硬编码（没有 `strings.xml`）
- **Build config**: 所有版本号和路径通过 Gradle version catalog (`libs.versions.*` / `libs.*`)

## Documentation

Miuix 组件文档在 `docs/zh_CN/` 路径下：

| Path | Content |
|---|---|
| `index.md` | VitePress 首页（hero + features） |
| `guide/getting-started.md` | 快速开始、添加依赖、基本用法 |
| `guide/colors.md` | 色板系统 |
| `guide/icons.md` | 图标使用 |
| `guide/theme.md` | 主题定制 |
| `guide/blur.md` | 模糊效果 |
| `guide/textstyles.md` | 文字样式 |
| `guide/navigation3.md` | Navigation3 集成 |
| `guide/multiplatform.md` | 跨平台配置 |
| `guide/best-practices.md` | 最佳实践 |
| `guide/utils.md` | 工具函数 |
| `components/` | 50+ 组件 API 文档（Markdown） |

## Miuix Component Categories (from docs)

Docs 中覆盖的组件按类别划分：

**Scaffold** — Scaffold

**Basic** — Surface / TopAppBar / NavigationBar / NavigationRail / TabRow / Card / BasicComponent / Button / IconButton / Text / SmallTitle / TextField / Switch / Checkbox / RadioButton / Slider / NumberPicker / ProgressIndicator / Snackbar / Icon / FloatingActionButton / FloatingToolbar / Divider / PullToRefresh / SearchBar / ColorPalette / ColorPicker

**Preference 扩展** — ArrowPreference / SwitchPreference / CheckboxPreference / RadioButtonPreference

**Overlay 弹窗族**（需在 Scaffold 中使用） — OverlayListPopup / OverlayCascadingListPopup / OverlayDropdownPreference / OverlaySpinnerPreference / OverlayDropdownMenu / OverlayIconDropdownMenu / OverlayIconCascadingDropdownMenu / OverlayBottomSheet / OverlayDialog

**Window 弹窗族**（窗口级，无需 Scaffold） — WindowListPopup / WindowCascadingListPopup / WindowDropdownPreference / WindowSpinnerPreference / WindowDropdownMenu / WindowIconDropdownMenu / WindowIconCascadingDropdownMenu / WindowBottomSheet / WindowDialog

## Next Steps (learning roadmap)

1. 扩展组件覆盖范围（Button / TextField / Dialog / BottomSheet 等尚未使用）
2. 实现 Navigation3 集成替换手动 NavPage 路由
3. 添加字符串资源支持（i18n）
4. 构建更复杂的多组件交互页面
5. 完善 AboutScreen 内容，当前仅占位
6. 清理 .gradle/configuration-cache/ 中大量过期缓存

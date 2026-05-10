# Compose Miuix Learning Project

一个基于 **Compose Multiplatform** + **Miuix** 的跨平台学习项目。

## 技术栈

- **Compose Multiplatform 1.10.3** — JetBrains 官方跨平台 UI 框架
- **Miuix 0.9.0** — HyperOS 设计风格的 Compose Multiplatform 组件库
- **Kotlin 2.3.20**
- **Navigation Compose** — 页面导航
- **Android / iOS** 双端支持

## 项目结构

```
composeApp/src/
├── commonMain/kotlin/fun/skyshadow/example/
│   ├── App.kt                               # 应用入口
│   └── presentation/
│       ├── component/                        # 可复用 UI 组件
│       │   └── GreetingCard.kt
│       └── screen/                           # 页面
│           └── HomeScreen.kt
├── androidMain/kotlin/fun/skyshadow/example/ # Android 平台入口
│   ├── MainActivity.kt
│   └── MainApplication.kt
└── iosMain/kotlin/fun/skyshadow/example/     # iOS 平台入口
    └── MainViewController.kt
```

## 快速开始

### Android

```bash
./gradlew :composeApp:assembleDebug
```

### iOS (macOS + Xcode)

用 Xcode 打开 `iosApp/iosApp.xcodeproj`，选择 iOS Simulator 运行。

## 学习路线

1. 从 `HomeScreen.kt` 开始了解页面组成
2. 查看 `GreetingCard.kt` 了解组件封装
3. 探索 Miuix 提供的其他组件：`Button`、`Card`、`NavigationBar`、`TopAppBar` 等
4. 尝试添加新的 screen 和 component，熟悉 Compose 的写法

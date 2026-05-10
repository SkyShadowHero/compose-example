# 仓库指南

## 项目结构
- `composeApp/src/commonMain/kotlin/fun/skyshadow/example/`: 共享 UI 代码
  - `App.kt`: 应用入口
  - `presentation/component/`: 可复用组件
  - `presentation/screen/`: 页面
- `composeApp/src/androidMain/`: Android 入口
- `composeApp/src/iosMain/`: iOS 入口

## 构建命令
- `./gradlew :composeApp:assembleDebug`: 构建 Android debug APK

## 编码风格
- Kotlin + Compose Multiplatform
- 4 空格缩进
- `PascalCase` 类名, `camelCase` 函数/变量

## 依赖项
- Compose Multiplatform 1.10.3
- Miuix 0.9.0 (HyperOS 风格组件库)
- Navigation Compose
- 详见 `gradle/libs.versions.toml`

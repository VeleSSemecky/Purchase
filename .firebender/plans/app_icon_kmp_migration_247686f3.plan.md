<!--firebender-plan
name: App Icon KMP Migration
overview: Правильно налаштувати іконку додатку для обох платформ: виправити зламаний adaptive icon в `androidApp` та створити `Assets.xcassets/AppIcon.appiconset` для iOS. Модуль `presentation` не чіпаємо — він залишається як reference.
todos:
  - id: android-copy-drawables
    content: "Copy ic_launcher_background.xml and ic_launcher_foreground.xml from shared/composeResources to androidApp/res/drawable"
  - id: android-fix-adaptive-icon
    content: "Fix mipmap-anydpi-v26/ic_launcher.xml and create ic_launcher_round.xml in androidApp"
  - id: ios-generate-png
    content: "Generate 1024x1024 AppIcon PNG using Python/Pillow from the vector design (teal background + white robot)"
  - id: ios-assets-structure
    content: "Create iosApp/iosApp/Assets.xcassets/ with Contents.json and AppIcon.appiconset/Contents.json"
  - id: ios-pbxproj-update
    content: "Update iosApp.xcodeproj/project.pbxproj to reference the new Assets.xcassets (PBXFileReference + PBXBuildFile + Group + Resources phase)"

-->


# App Icon KMP Migration

## Поточний стан

- `shared/composeResources/drawable/` — правильні вектори: `ic_launcher_background.xml` (teal `#4ACFAC`) + `ic_launcher_foreground.xml` (Android robot)
- `androidApp/res/mipmap-anydpi-v26/ic_launcher.xml` — **зламаний placeholder**: `@color/purple_500` + `@android:drawable/ic_dialog_info`
- `iosApp` — **немає `Assets.xcassets`** взагалі, але `project.pbxproj` очікує `AppIcon`
- `presentation` — залишається **без змін** як reference старого коду

---

## 1. Android — fix adaptive icon в `androidApp`

**Скопіювати вектори** з `shared` до `androidApp`:
- `shared/.../composeResources/drawable/ic_launcher_background.xml` → `androidApp/src/main/res/drawable/ic_launcher_background.xml`
- `shared/.../composeResources/drawable/ic_launcher_foreground.xml` → `androidApp/src/main/res/drawable/ic_launcher_foreground.xml`

**Виправити** `androidApp/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`:
```xml
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@drawable/ic_launcher_background"/>
    <foreground android:drawable="@drawable/ic_launcher_foreground"/>
</adaptive-icon>
```

**Створити** `androidApp/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml` (аналогічний вміст — Android застосовує круглу маску автоматично).

---

## 2. iOS — створити Assets.xcassets з AppIcon

**Згенерувати PNG** 1024x1024 через Python/Pillow — відтворити дизайн з векторів:
- фон: teal `#4ACFAC` (108x108dp scaled → 1024px)
- foreground: white Android robot paths з `ic_launcher_foreground.xml`

**Створити структуру файлів**:
```
iosApp/iosApp/Assets.xcassets/
    Contents.json
    AppIcon.appiconset/
        Contents.json           (iOS 12+ single 1024x1024 entry)
        AppIcon-1024.png
```

**Оновити `project.pbxproj`** — додати через Python-скрипт:
- `PBXFileReference` для `Assets.xcassets` (`folder.assetcatalog`)
- `PBXBuildFile` → Resources build phase
- Додати в `PBXGroup` `d6f9f693...` (iosApp group)
- Додати в `PBXResourcesBuildPhase` поряд з `GoogleService-Info.plist`



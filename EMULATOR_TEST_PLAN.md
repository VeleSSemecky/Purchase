# 📱 План тестування на емуляторі Android

## Коли можна буде запустити на емуляторі?

### ✅ Відповідь: Після Phase 2.3 (через 1-2 тижні)

---

## Етапи до запуску

### Phase 2.1 ✅ ЗАВЕРШЕНО
**Infrastructure Setup**
- ✅ Gradle dependencies
- ✅ Directory structure
- ✅ expect/actual pattern
- ✅ Koin DI setup
- ✅ Navigation framework
- ✅ mockDomain integration

**Статус:** Інфраструктура готова

---

### Phase 2.2 🔄 В ПРОЦЕСІ (Тиждень 1)
**ViewModels Migration**

**Поточний прогрес:**
- ✅ SettingsPurchaseViewModel (1/15)
- 🔄 Наступні ViewModels (~14)

**Мета Phase 2.2:**
- Мігрувати всі 15 ViewModels
- Замінити Dagger на Koin
- Адаптувати для KMP
- StateFlow замість LiveData

**Після Phase 2.2:**
- ViewModels готові ✅
- Можна створювати UI ✅
- Але ще НЕ можна запустити ❌

---

### Phase 2.3 🔜 НАСТУПНА (Тижні 2-3)
**UI Screens Migration**

**Що потрібно зробити:**
- Мігрувати ~50+ @Composable screens
- Підключити ViewModels
- Налаштувати навігацію
- Створити всі екрани

**Критичні екрани для демо:**
1. **MainScreen** ✅ (вже є placeholder)
2. **PurchaseListScreen** - список покупок
3. **PurchaseDetailScreen** - деталі покупки
4. **PurchaseEditScreen** - редагування
5. **SettingsScreen** ✅ (вже є)
6. **CollectionListScreen** - список колекцій

**Після Phase 2.3:**
- Всі екрани готові ✅
- Навігація працює ✅
- mockDomain дає дані ✅
- Майже готово до тестування ✅

---

### Phase 2.4 ⏭️ INTEGRATION (3-5 днів)
**Android App Integration**

**Що потрібно зробити:**

#### 1. Створити/Оновити Android App
```kotlin
// В androidApp або створити новий модуль
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Koin
        startKoin {
            androidContext(this@MainActivity)
            modules(appModules)
        }
        
        setContent {
            App() // З shared модуля!
        }
    }
}
```

#### 2. Application Class
```kotlin
class PurchaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this)
            modules(appModules)
        }
    }
}
```

#### 3. AndroidManifest.xml
```xml
<application
    android:name=".PurchaseApplication"
    android:theme="@style/Theme.Purchase">
    <activity
        android:name=".MainActivity"
        android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
</application>
```

#### 4. build.gradle.kts
```kotlin
dependencies {
    implementation(project(":shared"))
    implementation(project(":mockDomain"))
    // Koin Android
    implementation("io.insert-koin:koin-android:4.0.0")
}
```

**Після Phase 2.4:**
- ✅ Android app збирається
- ✅ Запускається на емуляторі
- ✅ ГОТОВО ДО ТЕСТУВАННЯ! 🎉

---

## 🎯 Milestone: Перший запуск на емуляторі

### Target Date: Після Phase 2.4 (~2-3 тижні)

### Що буде працювати:

#### ✅ Основна функціональність:
- Запуск app
- Main screen з навігацією
- Settings screen (повністю функціональний)
- Purchase list (з mock даними)
- Purchase detail
- Purchase edit
- Навігація між екранами

#### ✅ Mock дані:
- 5 mock покупок (молоко, хліб, книга, кава, телефон)
- 3 mock SKU
- Налаштування зберігаються
- Всі CRUD операції працюють в пам'яті

#### ✅ UI Features:
- Material3 design
- Type-safe navigation
- Reactive updates (StateFlow)
- Image loading (Coil)
- All controls working

#### ❌ Що НЕ буде працювати:
- Firebase (використовуємо mock дані)
- Real database (Room буде Phase 4)
- Push notifications (Phase 5)
- iOS app (теоретично готовий, але не протестований)

---

## 📋 Checklist для запуску на емуляторі

### Pre-launch (Phase 2.3 completion):
- [ ] Всі 15 ViewModels мігровані
- [ ] Мінімум 10 ключових екранів готові
- [ ] Навігація між екранами працює
- [ ] mockDomain підключений через Koin
- [ ] Всі екрани компілюються

### Integration (Phase 2.4):
- [ ] androidApp модуль створений/оновлений
- [ ] Application class з Koin init
- [ ] MainActivity з App() composable
- [ ] AndroidManifest налаштований
- [ ] Dependencies підключені
- [ ] Build успішний

### First Run:
- [ ] App запускається на емуляторі
- [ ] Main screen відображається
- [ ] Можна натиснути кнопки
- [ ] Навігація працює
- [ ] Mock дані відображаються
- [ ] Немає crashes

### Verification:
- [ ] Список покупок показує 5 items
- [ ] Можна відкрити деталі
- [ ] Можна редагувати
- [ ] Settings працює
- [ ] Зміни зберігаються (в пам'яті)
- [ ] Назад навігація працює

---

## 🚀 Quick Start для тестування (Phase 2.4)

### 1. Запуск емулятора
```bash
# Відкрити Android Studio
# AVD Manager → Pixel 6 (або інший)
# Start emulator
```

### 2. Build & Install
```bash
./gradlew :androidApp:assembleDebug
./gradlew :androidApp:installDebug

# Або з Android Studio:
# Run → Run 'androidApp'
```

### 3. Перевірка основного flow
```
1. App відкривається → Main screen
2. Натиснути "Purchase List"
3. Бачимо 5 mock покупок
4. Клік на "Молоко" → Detail screen
5. Бачимо ціну, кількість, тощо
6. Back → повернення до списку
7. Settings → налаштування
8. Змінити параметри → зберігається
9. Все працює! ✅
```

---

## 📊 Timeline Estimate

```
Сьогодні (Nov 29):     Phase 2.1 ✅ + Phase 2.2 started
Week 1 (Dec 1-5):      Phase 2.2 complete (ViewModels)
Week 2-3 (Dec 6-15):   Phase 2.3 (UI Screens)
Week 3 (Dec 16-20):    Phase 2.4 (Integration) 
                       
Target: ~Dec 20        ✅ ПЕРШИЙ ЗАПУСК НА ЕМУЛЯТОРІ!
```

### Оптимістичний сценарій: 2 тижні
Якщо швидко мігрувати ключові екрани

### Реалістичний сценарій: 3 тижні
З повною міграцією всіх екранів

### З буфером: 4 тижні
Якщо виникнуть проблеми

---

## 🎯 MVP для першого запуску

### Мінімальні екрани (достатньо для демо):

**Must Have (6 екранів):**
1. ✅ MainScreen - navigation hub
2. ✅ SettingsPurchaseScreen - вже працює
3. 🔄 PurchaseListScreen - список покупок
4. 🔄 PurchaseDetailScreen - деталі
5. 🔄 PurchaseEditScreen - додати/редагувати
6. 🔄 CollectionListScreen - колекції

**Nice to Have (+4 екрани):**
7. SkuListScreen
8. SkuDetailScreen
9. HistoryScreen
10. PhotoScreen

**Можна пропустити спочатку:**
- Login screen (немає auth)
- Biometric screen (Phase 5)
- Advanced settings
- Statistics

---

## 💡 Стратегія прискорення

### Щоб швидше запустити на емуляторі:

#### 1. Parallel Development ⚡
- Один розробник → ViewModels
- Інший → UI Screens
- Зустрічаємось в середині

#### 2. MVP First 🎯
- Спочатку 6 ключових екранів
- Решту можна потім
- Головне - щоб працював basic flow

#### 3. Copy-Paste Template 📋
- Створити template screen
- Copy для кожного нового екрану
- Швидко адаптувати

#### 4. Use Placeholders 🎨
- Спрощені екрани спочатку
- Красивий дизайн потім
- Функціональність > Красота

---

## ✅ Success Criteria

### Перший запуск вважається успішним якщо:

1. ✅ App запускається без crash
2. ✅ Main screen відображається
3. ✅ Можна відкрити список покупок
4. ✅ Бачимо mock дані (5 items)
5. ✅ Навігація працює (вперед/назад)
6. ✅ Можна відкрити деталі
7. ✅ Settings працює
8. ✅ Зміни зберігаються

**Це буде доказ концепції що міграція працює!** 🎉

---

## 📱 Alternative: Ранній тест (Phase 2.2)

### Можна зробити "super early" тест:

**Що вже є зараз:**
- ✅ MainScreen
- ✅ SettingsScreen
- ✅ Navigation
- ✅ mockDomain

**Швидке рішення (~1 година):**
1. Створити androidApp модуль
2. Додати MainActivity з App()
3. Запустити

**Результат:**
- Побачимо Main screen з кнопками
- Settings screen працюватиме
- Інші - placeholders

**Переваги:**
- ✅ Перевіримо що infrastructure працює
- ✅ Перевіримо Koin integration
- ✅ Перевіримо navigation
- ✅ Мотивація для команди! 🚀

**Хочете зробити такий early test зараз?**

---

## 🎊 Висновок

### Коли запускати на емуляторі?

**Офіційна відповідь:** Після Phase 2.4 (~2-3 тижні)

**Early test:** Можна прямо зараз (обмежена функціональність)

**Повний тест:** Phase 2.4 (всі екрани + mock дані)

**Production ready:** Phase 6 (з real data + domain)

---

**Вибір за вами!** 

Продовжити з Phase 2.2 (ViewModels) або зробити early test зараз? 🤔

---

_Created: November 29, 2025_  
_Target: Early test now or Full test in 2-3 weeks_  
_Recommendation: Continue Phase 2.2, early test optional_


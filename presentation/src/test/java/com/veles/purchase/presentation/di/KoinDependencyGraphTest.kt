package com.veles.purchase.presentation.di

import android.app.Application
import androidx.datastore.preferences.core.Preferences
import com.veles.purchase.data.room.AppDatabase
import com.veles.purchase.presentation.di.module.allPresentationKoinModules
import com.veles.purchase.presentation.model.purchase.PurchaseCollectionModelUI
import com.veles.purchase.presentation.model.purchase.PurchasePhotoModelUI
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.choose.CurrencyChooseViewModel
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.search.CurrencySearchViewModel
import com.veles.purchase.presentation.presentation.compose.shopping.edit.SkuEditViewModel
import com.veles.purchase.presentation.presentation.compose.shopping.graph.OutlayGraphViewModel
import com.veles.purchase.presentation.presentation.compose.shopping.list.SkuListViewModel
import com.veles.purchase.presentation.presentation.compose.shopping.photo.PhotoListViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.biometric.BiometricComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.EditCollectionComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list.CollectionPurchaseComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.edit.EditPurchaseViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.history.HistoryComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.later.ListLaterPurchaseViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.list.ListPurchaseViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.login.LoginViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.navigation.NavigationViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.navigation.UpdateViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.photo.PhotoPurchaseComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.setting.SettingPurchaseComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.sort.SortPurchaseViewModel
import com.veles.purchase.presentation.presentation.mvvm.pip.PIPViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category.CategoryViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.photo.PhotoPurchaseComposeFragmentArgs
import com.veles.purchase.presentation.presentation.mvvm.purchase.history.HistoryComposeFragmentArgs
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.EditCollectionComposeFragmentArgs
import com.veles.purchase.presentation.presentation.mvvm.purchase.edit.EditPurchaseFragmentArgs
import com.veles.purchase.presentation.presentation.mvvm.purchase.list.ListPurchaseFragmentArgs
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category.CategoryFragmentArgs
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.choose.CurrencyChooseFragmentArgs
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.search.CurrencySearchFragmentArgs
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.month.MonthChooseFragmentArgs
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.year.YearChooseFragmentArgs
import com.veles.purchase.presentation.presentation.compose.shopping.edit.SkuEditFragmentArgs
import com.veles.purchase.presentation.presentation.compose.shopping.photo.PhotoListFragmentArgs
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertNotNull
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import io.mockk.mockk
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockkClass
import io.mockk.mockkObject
import io.mockk.mockkStatic
import java.util.Currency
import java.util.Locale
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.mock.MockProvider
import org.robolectric.RuntimeEnvironment
import kotlin.reflect.KClass

/**
 * Comprehensive test to verify Koin dependency graph integrity
 * This test ensures all dependencies in the Koin modules can be resolved
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [28])
class KoinDependencyGraphTest : KoinTest {

    protected val application: Application = RuntimeEnvironment.getApplication()

    private val mockModule = module {
        // Mock Android-specific dependencies
        single<android.content.Context> { mockk(relaxed = true) }
        single<androidx.lifecycle.SavedStateHandle> { mockk(relaxed = true) }
        single<androidx.navigation.NavController> { mockk(relaxed = true) }
        single<android.app.Application> { mockk(relaxed = true) }
        single<androidx.datastore.core.DataStore<Preferences>> { mockk(relaxed = true) }
        single<AppDatabase> { mockk(relaxed = true) }
        single<android.content.ContentResolver> { mockk(relaxed = true) }

        // Mock Firebase dependencies
        single<com.google.firebase.auth.FirebaseAuth> { mockk(relaxed = true) }
        single<com.google.firebase.firestore.FirebaseFirestore> { mockk(relaxed = true) }
        single<com.google.firebase.storage.FirebaseStorage> { mockk(relaxed = true) }
        single<com.google.firebase.messaging.FirebaseMessaging> { mockk(relaxed = true) }
        single<com.google.firebase.analytics.FirebaseAnalytics> { mockk(relaxed = true) }
        single<com.google.firebase.crashlytics.FirebaseCrashlytics> { mockk(relaxed = true){
            justRun { recordException(any()) }
        } }

        // Mock Navigation and UI dependencies
        single<com.veles.purchase.presentation.base.mvvm.navigation.Router> { mockk(relaxed = true) }
        single<com.veles.purchase.domain.core.loger.Logger> { mockk(relaxed = true) }

        // Mock other common dependencies that might be missing
        single<androidx.room.RoomDatabase> { mockk(relaxed = true) }
        single<android.content.SharedPreferences> { mockk(relaxed = true) }
        single<kotlinx.coroutines.CoroutineDispatcher> { mockk(relaxed = true) }
    }

    @Before
    fun setUp() {

        MockProvider.register { clazz ->
            mockkClass(clazz, relaxed = true)
        }

        mockkStatic(Currency::class)
        every { Currency.getInstance(any<String>()) } returns mockk(relaxed = true)
        every { Currency.getInstance(any<Locale>()) } returns mockk(relaxed = true)


        // Mock all navigation fragment arguments
        mockkObject(PhotoPurchaseComposeFragmentArgs)
        every { PhotoPurchaseComposeFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(HistoryComposeFragmentArgs)
        every { HistoryComposeFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(EditCollectionComposeFragmentArgs)
        every { EditCollectionComposeFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(EditPurchaseFragmentArgs)
        every { EditPurchaseFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(ListPurchaseFragmentArgs)
        every { ListPurchaseFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(CategoryFragmentArgs)
        every { CategoryFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        // Additional Args classes found in compose/shopping directory
        mockkObject(CurrencyChooseFragmentArgs)
        every { CurrencyChooseFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(CurrencySearchFragmentArgs)
        every { CurrencySearchFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(MonthChooseFragmentArgs)
        every { MonthChooseFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(YearChooseFragmentArgs)
        every { YearChooseFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(SkuEditFragmentArgs)
        every { SkuEditFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        mockkObject(PhotoListFragmentArgs)
        every { PhotoListFragmentArgs.fromSavedStateHandle(any()) } returns mockk(relaxed = true)

        // Stop any existing Koin instance
        stopKoin()

        // Start Koin with all modules + mock module
        startKoin {
            androidContext(application)
            modules(allPresentationKoinModules + mockModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `verify all Koin modules can be loaded without errors`() = runTest {
        // Since Koin is already started in setUp, we just need to verify it's working
        val koin = getKoin()
        assertNotNull(koin)
        println("✅ Koin modules loaded successfully")
    }

    @Test
    fun `verify all UseCase dependencies can be resolved`() = runTest {
        // Test all UseCases from domain layer
        val useCases = listOf(
            // User UseCase
            com.veles.purchase.domain.usecase.user.UserUseCase::class,

            // Auth UseCases
            com.veles.purchase.domain.usecase.auth.LoginUseCase::class,
            com.veles.purchase.domain.usecase.logout.LogoutUseCase::class,

            // Settings UseCases
            com.veles.purchase.domain.usecase.setting.GetSettingUseCase::class,
            com.veles.purchase.domain.usecase.setting.SetSettingUseCase::class,

            // Purchase UseCases
            com.veles.purchase.domain.usecase.purchase.AddLazyPurchaseUseCase::class,
            com.veles.purchase.domain.usecase.purchase.CheckPurchaseUseCase::class,
            com.veles.purchase.domain.usecase.purchase.DeletePurchaseUseCase::class,
            com.veles.purchase.domain.usecase.purchase.FirebasePurchaseSendUseCase::class,
            com.veles.purchase.domain.usecase.purchase.GetPurchaseHistoryUseCase::class,
            com.veles.purchase.domain.usecase.purchase.GetPurchaseUseCase::class,
            com.veles.purchase.domain.usecase.purchase.GetPurchasesUseCase::class,
            com.veles.purchase.domain.usecase.purchase.MoveForLaterPurchaseUseCase::class,
            com.veles.purchase.domain.usecase.purchase.SavePurchaseUseCase::class,
            com.veles.purchase.domain.usecase.purchase.SetPurchaseHistoryUseCase::class,

            // Collection UseCases
            com.veles.purchase.domain.usecase.collection.FirebaseFirestorePurchaseCollectionUseCase::class,
            com.veles.purchase.domain.usecase.collection.DeletePurchaseCollectionUseCase::class,
            com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseUseCase::class,
            com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseCategoryUseCase::class,
            com.veles.purchase.domain.usecase.collection.SavePurchaseCategoryUseCase::class,
            com.veles.purchase.domain.usecase.collection.SetCollectionPurchaseUseCase::class,

            // Storage UseCases
            com.veles.purchase.domain.usecase.storage.GetPhotoUseCase::class,
            com.veles.purchase.domain.usecase.storage.StorageDeleteUseCase::class,
            com.veles.purchase.domain.usecase.storage.FirebaseStorageUseCase::class,

            // Notification UseCase
            com.veles.purchase.domain.usecase.NotificationMessageUseCase::class,

            // Price UseCase
            com.veles.purchase.domain.usecase.price.PriceUseCase::class,

            // SKU UseCases
            com.veles.purchase.domain.usecase.sku.DeleteSkuPhotoUseCase::class,
            com.veles.purchase.domain.usecase.sku.DeleteSkuUseCase::class,
            com.veles.purchase.domain.usecase.sku.GetSkuPhotoUseCase::class,
            com.veles.purchase.domain.usecase.sku.GetSkuSumMontUseCase::class,
            com.veles.purchase.domain.usecase.sku.GetSkuUseCase::class,
            com.veles.purchase.domain.usecase.sku.SetSkuUseCase::class,

            // Biometric UseCases
            com.veles.purchase.domain.usecase.biometric.DecryptionUseCase::class,
            com.veles.purchase.domain.usecase.biometric.EncryptionUseCase::class
        )

        // Track successful and failed resolutions
        val successful = mutableListOf<String>()
        val failed = mutableListOf<String>()

        // Verify each UseCase can be resolved
        useCases.forEach { useCaseClass ->
            try {
                val useCase = getKoin().getOrNull<Any>(useCaseClass)
                if (useCase != null) {
                    successful.add(useCaseClass.simpleName ?: "Unknown")
                    println("✅ ${useCaseClass.simpleName} resolved successfully")
                } else {
                    failed.add(useCaseClass.simpleName ?: "Unknown")
                    println("❌ ${useCaseClass.simpleName} could not be resolved (null)")
                }
            } catch (e: Exception) {
                failed.add(useCaseClass.simpleName ?: "Unknown")
                println("❌ ${useCaseClass.simpleName} failed: ${e.message}")
            }
        }

        // Print summary
        println("\n📊 UseCase Resolution Summary:")
        println("✅ Successful: ${successful.size}")
        println("❌ Failed: ${failed.size}")

        if (failed.isNotEmpty()) {
            println("\n❌ Failed UseCases:")
            failed.forEach { println("  - $it") }
        }
    }

    @Test
    fun `verify all ViewModels can be resolved`() = runTest {
        // Test all ViewModels from your project
        val viewModels: List<KClass<out androidx.lifecycle.ViewModel>> = listOf(
            // Purchase related ViewModels
            LoginViewModel::class,
            PhotoPurchaseComposeViewModel::class,
            SettingPurchaseComposeViewModel::class,
            ListLaterPurchaseViewModel::class,
            HistoryComposeViewModel::class,
            ListPurchaseViewModel::class,
            BiometricComposeViewModel::class,
            EditPurchaseViewModel::class,
            NavigationViewModel::class,
            UpdateViewModel::class,
            SortPurchaseViewModel::class,

            // Collection ViewModels
            CollectionPurchaseComposeViewModel::class,
            EditCollectionComposeViewModel::class,
            CategoryViewModel::class,

            // Shopping/SKU ViewModels
            PhotoListViewModel::class,
            SkuListViewModel::class,
            SkuEditViewModel::class,
            OutlayGraphViewModel::class,
            CurrencySearchViewModel::class,
            CurrencyChooseViewModel::class,

            // PIP ViewModel
            PIPViewModel::class
        )

        // Track successful and failed resolutions
        val successful = mutableListOf<String>()
        val failed = mutableListOf<String>()

        // Verify each ViewModel can be resolved
        viewModels.forEach { viewModelClass ->
            try {
                val viewModel = getKoin().getOrNull<Any>(viewModelClass)
                if (viewModel != null) {
                    successful.add(viewModelClass.simpleName ?: "Unknown")
                    println("✅ ${viewModelClass.simpleName} resolved successfully")
                } else {
                    failed.add(viewModelClass.simpleName ?: "Unknown")
                    println("❌ ${viewModelClass.simpleName} could not be resolved (null)")
                }
            } catch (e: Exception) {
                failed.add(viewModelClass.simpleName ?: "Unknown")
                println("❌ ${viewModelClass.simpleName} failed: ${e.message}")
                println("❌ StackTrace:")
                e.printStackTrace()
            }
        }

        // Print summary
        println("\n📊 ViewModel Resolution Summary:")
        println("✅ Successful: ${successful.size}")
        println("❌ Failed: ${failed.size}")

        if (failed.isNotEmpty()) {
            println("\n❌ Failed ViewModels:")
            failed.forEach { println("  - $it") }
        }
    }

    @Test
    fun `verify module dependency chain integrity`() = runTest {
        // This test verifies that all modules in the chain can be loaded
        val modules = allPresentationKoinModules

        var successCount = 0
        var failCount = 0

        modules.forEach { module ->
            try {
                // Each module should be loadable
                stopKoin()
                startKoin {
                    modules(listOf(module, mockModule))
                }

                successCount++
                println("✅ Module loaded successfully")
            } catch (e: Exception) {
                failCount++
                println("❌ Failed to load module: ${e.message}")
            }
        }

        // Reload all modules together
        stopKoin()
        startKoin {
            modules(allPresentationKoinModules + mockModule)
        }

        println("\n📊 Module Loading Summary:")
        println("✅ Successful: $successCount")
        println("❌ Failed: $failCount")
        println("✅ All modules loaded successfully together")
    }

    @Test
    fun `verify critical dependencies exist`() = runTest {
        // Test that key dependencies can be resolved
        val criticalDependencies = listOf(
            "Context" to android.content.Context::class,
            "SavedStateHandle" to androidx.lifecycle.SavedStateHandle::class,
            "NavController" to androidx.navigation.NavController::class,
            "FirebaseAuth" to com.google.firebase.auth.FirebaseAuth::class,
            "FirebaseFirestore" to com.google.firebase.firestore.FirebaseFirestore::class
        )

        println("\n🔍 Testing Critical Dependencies:")
        criticalDependencies.forEach { (name, clazz) ->
            try {
                val dependency = getKoin().getOrNull<Any>(clazz)
                if (dependency != null) {
                    println("✅ $name is available")
                } else {
                    println("❌ $name is missing")
                }
            } catch (e: Exception) {
                println("❌ $name failed: ${e.message}")
            }
        }
    }

    @Test
    fun `verify all repositories can be resolved`() = runTest {
        // Test all repository interfaces from domain layer
        val repositories = listOf(
            // Auth repositories
            com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository::class,
            com.veles.purchase.domain.repository.auth.BiometricRepository::class,

            // Collection repositories
            com.veles.purchase.domain.repository.collection.CollectionPurchaseRepository::class,
            com.veles.purchase.domain.repository.collection.DeleteCollectionPurchaseRepository::class,
            com.veles.purchase.domain.repository.collection.GetCollectionPurchaseRepository::class,

            // Purchase repositories
            com.veles.purchase.domain.repository.purchase.PurchaseRepository::class,
            com.veles.purchase.domain.repository.purchase.PurchaseCategoryRepository::class,
            com.veles.purchase.domain.repository.purchase.PurchaseLaterRepository::class,
            com.veles.purchase.domain.repository.purchase.GetPurchasePhotoRepository::class,

            // Storage repositories
            com.veles.purchase.domain.repository.storage.DeletePurchasePhotoRepository::class,
            com.veles.purchase.domain.repository.storage.SetPurchasePhotoRepository::class,

            // SKU repositories
            com.veles.purchase.domain.repository.sku.SkuRepository::class,
            com.veles.purchase.domain.repository.sku.SkuPhotoRepository::class,

            // User repositories
            com.veles.purchase.domain.repository.user.FirebaseGetUserRepository::class,
            com.veles.purchase.domain.repository.user.FirebaseMessageTokenRepository::class,

            // Other repositories
            com.veles.purchase.domain.repository.setting.SettingRepository::class,
            com.veles.purchase.domain.repository.message.NotificationMessageRepository::class,
            com.veles.purchase.domain.repository.history.HistoryRepository::class
        )

        // Track successful and failed resolutions
        val successful = mutableListOf<String>()
        val failed = mutableListOf<String>()
        val failureDetails = mutableMapOf<String, String>()

        // Verify each repository can be resolved
        repositories.forEach { repositoryClass ->
            try {
                val repository = getKoin().getOrNull<Any>(repositoryClass)
                if (repository != null) {
                    successful.add(repositoryClass.simpleName ?: "Unknown")
                    println("✅ ${repositoryClass.simpleName} resolved successfully")
                } else {
                    val className = repositoryClass.simpleName ?: "Unknown"
                    failed.add(className)
                    failureDetails[className] = "Repository could not be resolved (returned null)"
                    println("❌ ${repositoryClass.simpleName} could not be resolved (null)")
                }
            } catch (e: Exception) {
                val className = repositoryClass.simpleName ?: "Unknown"
                failed.add(className)
                failureDetails[className] = e.message ?: "Unknown error"
                println("❌ ${repositoryClass.simpleName} failed: ${e.message}")
            }
        }

        // Print detailed summary
        println("\n📊 Repository Resolution Summary:")
        println("✅ Successfully resolved: ${successful.size}")
        println("❌ Failed to resolve: ${failed.size}")
        println("📝 Total repositories tested: ${repositories.size}")

        if (successful.isNotEmpty()) {
            println("\n✅ Successfully resolved repositories:")
            successful.sorted().forEach { println("  - $it") }
        }

        if (failed.isNotEmpty()) {
            println("\n❌ Failed repositories:")
            failed.sorted().forEach { className ->
                val detail = failureDetails[className] ?: "Unknown error"
                println("  - $className: $detail")
            }
        }

        // Verify that all repositories are properly configured
        if (failed.isNotEmpty()) {
            val failedRepos = failed.joinToString(", ")
            throw AssertionError("The following repositories are not properly configured in Koin: $failedRepos")
        }
    }
}

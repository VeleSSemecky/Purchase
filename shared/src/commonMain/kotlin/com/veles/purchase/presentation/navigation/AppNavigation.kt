package com.veles.purchase.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import com.veles.purchase.presentation.compose.login.LoginScreen
import com.veles.purchase.presentation.compose.main.MainScreen
import com.veles.purchase.presentation.compose.purchase.category.CategoryScreen
import com.veles.purchase.presentation.compose.purchase.collection.CollectionEditScreen
import com.veles.purchase.presentation.compose.purchase.collection.CollectionListScreen
import com.veles.purchase.presentation.compose.purchase.edit.PurchaseEditScreen
import com.veles.purchase.presentation.compose.purchase.history.HistoryScreen
import com.veles.purchase.presentation.compose.purchase.later.ListLaterScreen
import com.veles.purchase.presentation.compose.purchase.list.PurchaseListScreen
import com.veles.purchase.presentation.compose.purchase.setting.SettingsPurchaseScreen
import com.veles.purchase.presentation.compose.sku.edit.SkuEditScreen
import com.veles.purchase.presentation.compose.sku.list.SkuListScreen

private val navSavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.Login::class)
            subclass(Route.Main::class)
            subclass(Route.Collection.List::class)
            subclass(Route.Collection.Edit::class)
            subclass(Route.Collection.Category::class)
            subclass(Route.Collection.History::class)
            subclass(Route.Purchase.List::class)
            subclass(Route.Purchase.Detail::class)
            subclass(Route.Purchase.Edit::class)
            subclass(Route.Purchase.History::class)
            subclass(Route.Purchase.Later::class)
            subclass(Route.Sku.List::class)
            subclass(Route.Sku.Detail::class)
            subclass(Route.Sku.Edit::class)
            subclass(Route.Sku.Statistics::class)
            subclass(Route.Settings.Main::class)
            subclass(Route.Settings.Purchase::class)
            subclass(Route.Settings.Appearance::class)
            subclass(Route.Auth.Login::class)
            subclass(Route.Auth.Biometric::class)
        }
    }
}

/**
 * Main navigation graph for the application
 */
@Composable
fun AppNavigation(
    startDestination: Route = Route.Main,
    activity: Any? = null
) {
    val backStack = rememberNavBackStack(navSavedStateConfiguration, startDestination)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Login> {
                LoginScreen(
                    activity = activity,
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(Route.Main)
                    }
                )
            }
            entry<Route.Main> {
                MainScreen(
                    onNavigateToRoute = { backStack.add(it) },
                    onLogout = {
                        backStack.clear()
                        backStack.add(Route.Login)
                    }
                )
            }
            entry<Route.Collection.List> {
                CollectionListScreen(
                    onNavigateToCollection = { collectionId ->
                        backStack.add(Route.Purchase.List(collectionId))
                    },
                    onNavigateToAddCollection = {
                        backStack.add(Route.Collection.Edit())
                    }
                )
            }
            entry<Route.Collection.Edit> { route ->
                CollectionEditScreen(
                    collectionId = route.collectionId,
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToCategory = { collectionId ->
                        backStack.add(Route.Collection.Category(collectionId))
                    },
                    onNavigateToHistory = { collectionId ->
                        backStack.add(Route.Collection.History(collectionId))
                    }
                )
            }
            entry<Route.Collection.Category> { route ->
                CategoryScreen(
                    collectionId = route.collectionId,
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }

            entry<Route.Collection.History> { route ->
                HistoryScreen(
                    collectionId = route.collectionId,
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }

            entry<Route.Purchase.List> { route ->
                PurchaseListScreen(
                    collectionId = route.collectionId,
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToSettings = {
                        backStack.add(Route.Collection.Edit(route.collectionId))
                    },
                    onNavigateToPurchaseDetail = { purchaseId ->
                        backStack.add(Route.Purchase.Edit(purchaseId, route.collectionId))
                    }
                )
            }
            entry<Route.Purchase.Detail> { route ->
                PlaceholderScreen("Purchase Detail: ${route.purchaseId}")
            }
            entry<Route.Purchase.Edit> { route ->
                PurchaseEditScreen(
                    collectionId = route.collectionId,
                    purchaseId = route.purchaseId ?: "",
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.Purchase.Later> { route ->
                ListLaterScreen(
                    collectionId = route.collectionId,
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToPurchaseEdit = { purchaseId ->
                        backStack.add(Route.Purchase.Edit(purchaseId, route.collectionId))
                    }
                )
            }
            entry<Route.Sku.List> {
                SkuListScreen(
                    onNavigateToSkuEdit = { skuId ->
                        backStack.add(if (skuId != null) Route.Sku.Edit(skuId) else Route.Sku.Edit())
                    },
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToStatistics = {
                        backStack.add(Route.Sku.Statistics)
                    }
                )
            }
            entry<Route.Sku.Detail> { route ->
                PlaceholderScreen("SKU Detail: ${route.skuId}")
            }
            entry<Route.Sku.Edit> { route ->
                SkuEditScreen(
                    skuId = route.skuId ?: "",
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.Sku.Statistics> {
                com.veles.purchase.presentation.compose.sku.statistics.SkuStatisticsScreen(
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.Settings.Main> {
                PlaceholderScreen("Settings - Coming Soon")
            }
            entry<Route.Settings.Purchase> {
                SettingsPurchaseScreen(
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.Auth.Login> {
                PlaceholderScreen("Login - Coming Soon")
            }
            entry<Route.Auth.Biometric> {
                PlaceholderScreen("Biometric - Coming Soon")

            }
        }
    )
}

/**
 * Placeholder screen for routes not yet migrated
 */
@Composable
private fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

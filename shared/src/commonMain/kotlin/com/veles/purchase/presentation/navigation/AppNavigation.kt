package com.veles.purchase.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.veles.purchase.presentation.compose.login.LoginScreen
import com.veles.purchase.presentation.compose.main.MainScreen
import com.veles.purchase.presentation.compose.purchase.category.CategoryScreen
import com.veles.purchase.presentation.compose.purchase.collection.CollectionEditScreen
import com.veles.purchase.presentation.compose.purchase.collection.CollectionListScreen
import com.veles.purchase.presentation.compose.purchase.collection.CollectionMembersScreen
import com.veles.purchase.presentation.compose.purchase.edit.PurchaseEditScreen
import com.veles.purchase.presentation.compose.purchase.history.HistoryScreen
import com.veles.purchase.presentation.compose.purchase.later.ListLaterScreen
import com.veles.purchase.presentation.compose.purchase.list.PurchaseListScreen
import com.veles.purchase.presentation.compose.purchase.photo.PhotoViewerScreen
import com.veles.purchase.presentation.compose.purchase.setting.SettingsPurchaseScreen
import com.veles.purchase.presentation.compose.sku.edit.SkuEditScreen
import com.veles.purchase.presentation.compose.sku.list.SkuListScreen
import com.veles.purchase.presentation.mvvm.purchase.collection.EditCollectionComposeViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

private val navSavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.Login::class)
            subclass(Route.Main::class)
            subclass(Route.Collection.List::class)
            subclass(Route.Collection.Edit::class)
            subclass(Route.Collection.Category::class)
            subclass(Route.Collection.History::class)
            subclass(Route.Collection.Members::class)
            subclass(Route.Purchase.List::class)
            subclass(Route.Purchase.Edit::class)
            subclass(Route.Purchase.PhotoViewer::class)
            subclass(Route.Purchase.History::class)
            subclass(Route.Purchase.Later::class)
            subclass(Route.Sku.List::class)
            subclass(Route.Sku.Edit::class)
            subclass(Route.Sku.Statistics::class)
            subclass(Route.Settings.Main::class)
            subclass(Route.Settings.Purchase::class)
            subclass(Route.Settings.Appearance::class)
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
    val resultStore = rememberResultStore()
    val navigator = remember(backStack, resultStore) { Navigator(backStack, resultStore) }

    NavDisplay(
        backStack = backStack,
        onBack = { navigator.goBack() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            authDestinations(navigator, activity)
            mainDestinations(navigator)
            collectionDestinations(navigator)
            purchaseDestinations(navigator)
            skuDestinations(navigator)
            settingsDestinations(navigator)
        }
    )
}

/**
 * Navigator handles navigation actions by updating the [NavBackStack].
 */
class Navigator(private val backStack: NavBackStack<NavKey>, private val resultStore: ResultStore) {
    fun navigate(route: NavKey) {
        backStack.add(route)
    }

    fun goBack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    fun clearAndNavigate(route: NavKey) {
        backStack.clear()
        backStack.add(route)
    }

    fun setResult(key: String, value: Any?) {
        resultStore.setResult(key, value)
    }

    fun <T> consumeResult(key: String): T? = resultStore.consumeResult(key)
}

/**
 * ResultStore manages results between destinations.
 * Based on: https://developer.android.com/guide/navigation/navigation-3/recipes/results-state
 */
class ResultStore(initialMap: Map<String, Any?> = emptyMap()) {
    private val results = mutableStateMapOf<String, Any?>().apply { putAll(initialMap) }

    fun setResult(key: String, value: Any?) {
        results[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> consumeResult(key: String): T? = results.remove(key) as? T

    fun toMap(): Map<String, Any?> = HashMap(results)
}

val ResultStoreSaver: Saver<ResultStore, *> = Saver(
    save = {
        val map = it.toMap()
        if (map.isEmpty()) {
            null
        } else {
            val list = ArrayList<ArrayList<Any?>>()
            map.forEach { (k, v) ->
                list.add(arrayListOf(k, v))
            }
            list
        }
    },
    restore = {
        val list = it as? List<List<Any?>>
        val map = list?.associate { inner -> (inner[0] as String) to inner[1] } ?: emptyMap()
        ResultStore(map)
    }
)

@Composable
fun rememberResultStore(): ResultStore = rememberSaveable(saver = ResultStoreSaver) {
    ResultStore()
}

private fun EntryProviderScope<NavKey>.authDestinations(navigator: Navigator, activity: Any?) {
    entry<Route.Login> {
        LoginScreen(
            activity = activity,
            onLoginSuccess = { navigator.clearAndNavigate(Route.Main) }
        )
    }
    entry<Route.Auth.Biometric> {
        PlaceholderScreen("Biometric - Coming Soon")
    }
}

private fun EntryProviderScope<NavKey>.mainDestinations(navigator: Navigator) {
    entry<Route.Main> {
        MainScreen(
            onNavigateToRoute = { navigator.navigate(it) },
            onLogout = { navigator.clearAndNavigate(Route.Login) }
        )
    }
}

private fun EntryProviderScope<NavKey>.collectionDestinations(
    navigator: Navigator
) {
    entry<Route.Collection.List> {
        CollectionListScreen(
            onNavigateToCollection = { collectionId ->
                navigator.navigate(Route.Purchase.List(collectionId))
            },
            onNavigateToAddCollection = {
                navigator.navigate(Route.Collection.Edit())
            }
        )
    }
    entry<Route.Collection.Edit> { route ->
        val viewModel: EditCollectionComposeViewModel = koinViewModel(
            parameters = { parametersOf(route.collectionId) }
        )

        // Handle results from other screens
        navigator.consumeResult<List<String>>("members_selection")?.let { selectedIds ->
            viewModel.onMembersSelected(selectedIds)
        }

        CollectionEditScreen(
            viewModel = viewModel,
            collectionId = route.collectionId,
            onNavigateBack = { navigator.goBack() },
            onNavigateToCategory = { collectionId ->
                navigator.navigate(Route.Collection.Category(collectionId))
            },
            onNavigateToHistory = { collectionId ->
                navigator.navigate(Route.Collection.History(collectionId))
            },
            onNavigateToMembers = { collectionId, selectedIds ->
                navigator.navigate(Route.Collection.Members(collectionId, selectedIds))
            }
        )
    }
    entry<Route.Collection.Category> { route ->
        CategoryScreen(
            collectionId = route.collectionId,
            onNavigateBack = { navigator.goBack() }
        )
    }
    entry<Route.Collection.History> { route ->
        HistoryScreen(
            collectionId = route.collectionId,
            onNavigateBack = { navigator.goBack() }
        )
    }
    entry<Route.Collection.Members> { route ->
        CollectionMembersScreen(
            initialSelectedIds = route.selectedIds,
            onNavigateBack = { navigator.goBack() },
            onConfirm = { selectedIds ->
                navigator.setResult("members_selection", ArrayList(selectedIds))
                navigator.goBack()
            }
        )
    }
}

private fun EntryProviderScope<NavKey>.purchaseDestinations(navigator: Navigator) {
    entry<Route.Purchase.List> { route ->
        PurchaseListScreen(
            collectionId = route.collectionId,
            onNavigateBack = { navigator.goBack() },
            onNavigateToSettings = {
                navigator.navigate(Route.Collection.Edit(route.collectionId))
            },
            onNavigateToPurchaseDetail = { purchaseId ->
                navigator.navigate(Route.Purchase.Edit(purchaseId, route.collectionId))
            }
        )
    }
    entry<Route.Purchase.Edit> { route ->
        PurchaseEditScreen(
            collectionId = route.collectionId,
            purchaseId = route.purchaseId ?: "",
            onNavigateBack = { navigator.goBack() },
            onNavigateToPhoto = { photoUri ->
                navigator.navigate(Route.Purchase.PhotoViewer(photoUri))
            }
        )
    }
    entry<Route.Purchase.PhotoViewer> { route ->
        PhotoViewerScreen(
            photoUri = route.photoUri,
            onNavigateBack = { navigator.goBack() }
        )
    }
    entry<Route.Purchase.Later> { route ->
        ListLaterScreen(
            collectionId = route.collectionId,
            onNavigateBack = { navigator.goBack() },
            onNavigateToPurchaseEdit = { purchaseId ->
                navigator.navigate(Route.Purchase.Edit(purchaseId, route.collectionId))
            }
        )
    }
    entry<Route.Purchase.History> { route ->
        PlaceholderScreen("Purchase History: ${route.collectionId}")
    }
}

private fun EntryProviderScope<NavKey>.skuDestinations(navigator: Navigator) {
    entry<Route.Sku.List> {
        SkuListScreen(
            onNavigateToSkuEdit = { skuId ->
                navigator.navigate(Route.Sku.Edit(skuId))
            },
            onNavigateBack = { navigator.goBack() },
            onNavigateToStatistics = {
                navigator.navigate(Route.Sku.Statistics)
            }
        )
    }
    entry<Route.Sku.Edit> { route ->
        SkuEditScreen(
            skuId = route.skuId ?: "",
            onNavigateBack = { navigator.goBack() }
        )
    }
    entry<Route.Sku.Statistics> {
        com.veles.purchase.presentation.compose.sku.statistics.SkuStatisticsScreen(
            onNavigateBack = { navigator.goBack() }
        )
    }
}

private fun EntryProviderScope<NavKey>.settingsDestinations(navigator: Navigator) {
    entry<Route.Settings.Main> {
        PlaceholderScreen("Settings - Coming Soon")
    }
    entry<Route.Settings.Purchase> {
        SettingsPurchaseScreen(
            onNavigateBack = { navigator.goBack() }
        )
    }
    entry<Route.Settings.Appearance> {
        PlaceholderScreen("Appearance - Coming Soon")
    }
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

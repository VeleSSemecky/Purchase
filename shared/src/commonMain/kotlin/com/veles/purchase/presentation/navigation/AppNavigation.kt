package com.veles.purchase.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.veles.purchase.platform.biometric.BiometricAuthenticator
import com.veles.purchase.presentation.compose.main.MainScreen
import com.veles.purchase.presentation.compose.purchase.biometric.BiometricScreen
import com.veles.purchase.presentation.compose.purchase.category.CategoryScreen
import com.veles.purchase.presentation.compose.purchase.collection.CollectionEditScreen
import com.veles.purchase.presentation.compose.purchase.collection.CollectionListScreen
import com.veles.purchase.presentation.compose.purchase.edit.PurchaseEditScreen
import com.veles.purchase.presentation.compose.purchase.history.HistoryScreen
import com.veles.purchase.presentation.compose.purchase.list.PurchaseListScreen
import com.veles.purchase.presentation.compose.purchase.setting.SettingsPurchaseScreen
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

/**
 * Main navigation graph for the application
 *
 * This will be expanded as we migrate screens from presentation module
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: Route = Route.Main,
    activity: Any? = null
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Main screen
        composable<Route.Main> {
            MainScreen(navController = navController)
        }

        // Collection navigation
        composable<Route.Collection.List> {
            CollectionListScreen(
                onNavigateToCollection = { collectionId ->
                    navController.navigate(Route.Purchase.List(collectionId))
                },
                onNavigateToAddCollection = {
                    navController.navigate(Route.Collection.Edit())
                }
            )
        }

        composable<Route.Collection.Edit> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.Collection.Edit>()
            CollectionEditScreen(
                collectionId = args.collectionId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCategory = { collectionId ->
                    navController.navigate(Route.Collection.Category(collectionId))
                },
                onNavigateToHistory = { collectionId ->
                    navController.navigate(Route.Collection.History(collectionId))
                }
            )
        }

        composable<Route.Collection.Category> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.Collection.Category>()
            CategoryScreen(
                collectionId = args.collectionId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.Collection.History> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.Collection.History>()
            HistoryScreen(
                collectionId = args.collectionId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Purchase navigation
        composable<Route.Purchase.List> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.Purchase.List>()
            PurchaseListScreen(
                collectionId = args.collectionId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSettings = {
                    navController.navigate(Route.Collection.Edit(args.collectionId))
                },
                onNavigateToPurchaseDetail = { purchaseId ->
                    // Navigate to edit screen to edit the purchase
                    navController.navigate(Route.Purchase.Edit(purchaseId, args.collectionId))
                }
            )
        }

        composable<Route.Purchase.Detail> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.Purchase.Detail>()
            PlaceholderScreen("Purchase Detail: ${args.purchaseId}")
        }

        composable<Route.Purchase.Edit> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.Purchase.Edit>()
            PurchaseEditScreen(
                collectionId = args.collectionId,
                purchaseId = args.purchaseId ?: "", // Empty string for new purchase
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // SKU navigation
        composable<Route.Sku.List> {
            PlaceholderScreen("SKU List - Coming Soon")
        }

        composable<Route.Sku.Detail> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.Sku.Detail>()
            PlaceholderScreen("SKU Detail: ${args.skuId}")
        }

        // Settings navigation
        composable<Route.Settings.Main> {
            PlaceholderScreen("Settings - Coming Soon")
        }

        composable<Route.Settings.Purchase> {
            SettingsPurchaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Auth navigation
        composable<Route.Auth.Login> {
            PlaceholderScreen("Login - Coming Soon")
        }

        composable<Route.Auth.Biometric> {
            // Create BiometricAuthenticator from activity (Android only)
            if (activity != null) {
                val authenticator: BiometricAuthenticator = koinInject(
                    parameters = { parametersOf(activity) }
                )
                BiometricScreen(
                    biometricAuthenticator = authenticator,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            } else {
                PlaceholderScreen("Biometric Auth requires activity context")
            }
        }
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


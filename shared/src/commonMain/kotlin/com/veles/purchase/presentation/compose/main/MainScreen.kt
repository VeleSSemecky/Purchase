package com.veles.purchase.presentation.compose.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.veles.purchase.presentation.compose.purchase.collection.CollectionListScreen
import com.veles.purchase.presentation.compose.purchase.setting.SettingsPurchaseScreen
import com.veles.purchase.presentation.navigation.Route
import com.veles.purchase.shared.resources.Res
import com.veles.purchase.shared.resources.ic_baseline_camera_alt_24
import com.veles.purchase.shared.resources.ic_baseline_payment_24
import com.veles.purchase.shared.resources.ic_baseline_settings_24
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

/**
 * Main Screen with Navigation Drawer
 *
 * Migrated from: /Users/yuriimelnyk/StudioProjects/Purchase/presentation/src/main/java/com/veles/purchase/presentation/presentation/mvvm/purchase/navigation/NavigationFragment.kt
 *
 * Original design:
 * - Navigation drawer with user info and menu items
 * - Dark theme (colorPrimaryDark = #303030, colorPrimary = #212121)
 * - Green accent color (gr = #4ACFAC)
 * - Menu items: SKU List, PIP (camera), Settings
 * - Toolbar with menu icon and title "Collection List"
 *
 * Phase 2.4 - Main navigation screen migration
 */

// Original colors from presentation module
object NavigationColors {
    val colorPrimary = Color(0xff212121)
    val colorPrimaryDark = Color(0xff303030)
    val colorAccent = Color(0xff424242)
    val gr = Color(0xff4ACFAC)  // Green accent
    val surface = Color(0xFF121212)
}

/**
 * Main screen with navigation drawer and nested navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Transparent,
        drawerContent = {
            DrawerContent(
                drawerState = drawerState,
                onSkuListClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Route.Sku.List)
                },
                onPipClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to PIP when migrated
                },
                onSettingsClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Route.Settings.Purchase)
                },
                onSignOutClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Implement logout when auth is migrated
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                MainToolbar(
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                // Collection List Screen (migrated in Phase 2.5!)
                CollectionListScreen(
                    onNavigateToCollection = { collectionId ->
                        // Navigate to PurchaseListScreen with collectionId (Phase 2.6!)
                        navController.navigate(Route.Purchase.List(collectionId))
                    },
                    onNavigateToAddCollection = {
                        // Navigate to EditCollectionScreen (Phase 2.8!)
                        navController.navigate(Route.Collection.Edit())
                    }
                )
            }
        }
    }
}

/**
 * Navigation drawer content with user header and menu items
 */
@Composable
private fun DrawerContent(
    drawerState: DrawerState,
    onSkuListClick: () -> Unit,
    onPipClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .background(NavigationColors.colorPrimaryDark)
        ) {
            DrawerHeader(onSignOutClick = onSignOutClick)
            DrawerMenuItems(
                onSkuListClick = onSkuListClick,
                onPipClick = onPipClick,
                onSettingsClick = onSettingsClick
            )
        }
    }
}

/**
 * Drawer header with user info (placeholder for now)
 */
@Composable
private fun DrawerHeader(onSignOutClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // User avatar placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(NavigationColors.gr),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "U",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
            }

            // Sign out icon (placeholder)
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSignOutClick() }
                    .background(Color.Gray)
            )
        }

        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "User Name",  // TODO: Get from user repository when migrated
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            text = "user@example.com",  // TODO: Get from user repository when migrated
            color = Color.Gray
        )
    }
}

/**
 * Drawer menu items
 */
@Composable
private fun DrawerMenuItems(
    onSkuListClick: () -> Unit,
    onPipClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))

    // SKU List / History
    DrawerMenuItem(
        text = "History Pays",  // Original: R.string.history_pays
        iconResource = Res.drawable.ic_baseline_payment_24,
        onClick = onSkuListClick
    )

    // PIP (Picture in Picture / Camera)
    DrawerMenuItem(
        text = "PIP",  // Original: R.string.pip
        iconResource = Res.drawable.ic_baseline_camera_alt_24,
        onClick = onPipClick
    )

    // Settings
    DrawerMenuItem(
        text = "Settings",  // Original: R.string.setting
        iconResource = Res.drawable.ic_baseline_settings_24,
        onClick = onSettingsClick
    )
}

/**
 * Individual drawer menu item
 */
@Composable
private fun DrawerMenuItem(
    text: String,
    iconResource: org.jetbrains.compose.resources.DrawableResource,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconResource),
            contentDescription = text,
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.White
        )
    }
}

/**
 * Main toolbar with menu icon and title
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainToolbar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                text = "Collection List",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = NavigationColors.colorPrimary
        )
    )
}

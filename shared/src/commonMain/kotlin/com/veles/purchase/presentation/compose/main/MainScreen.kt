package com.veles.purchase.presentation.compose.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.presentation.compose.purchase.collection.CollectionListScreen
import com.veles.purchase.presentation.navigation.Route
import com.veles.purchase.presentation.viewmodel.main.MainViewModel
import com.veles.purchase.shared.resources.Res
import com.veles.purchase.shared.resources.ic_baseline_camera_alt_24
import com.veles.purchase.shared.resources.ic_baseline_payment_24
import com.veles.purchase.shared.resources.ic_baseline_settings_24
import com.veles.purchase.shared.resources.ic_outline_sensor_door
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

// App colors (matches MyTheme / Colors.kt)
private object NavColors {
    val primary = Color(0xFF212121)
    val primaryDark = Color(0xFF181818)
    val green = Color(0xFF38A186)
    val divider = Color(0xFF2E2E2E)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToRoute: (Route) -> Unit,
    onLogout: () -> Unit,
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        drawerContent = {
            DrawerContent(
                displayName = state.displayName,
                email = state.email,
                initials = state.initials,
                onSkuListClick = {
                    scope.launch { drawerState.close() }
                    onNavigateToRoute(Route.Sku.List)
                },
                onPipClick = {
                    scope.launch { drawerState.close() }
                },
                onSettingsClick = {
                    scope.launch { drawerState.close() }
                    onNavigateToRoute(Route.Settings.Purchase)
                },
                onSignOutClick = {
                    scope.launch { drawerState.close() }
                    viewModel.logout {
                        onLogout()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                MainToolbar(onMenuClick = { scope.launch { drawerState.open() } })
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                CollectionListScreen(
                    onNavigateToCollection = { collectionId ->
                        onNavigateToRoute(Route.Purchase.List(collectionId))
                    },
                    onNavigateToAddCollection = {
                        onNavigateToRoute(Route.Collection.Edit())
                    }
                )
            }
        }
    }
}

// ── Drawer ────────────────────────────────────────────────────────────────────

@Composable
private fun DrawerContent(
    displayName: String,
    email: String,
    initials: String,
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
                .fillMaxWidth(0.82f)
                .background(NavColors.primaryDark)
        ) {
            DrawerHeader(
                displayName = displayName,
                email = email,
                initials = initials,
                onSignOutClick = onSignOutClick
            )

            HorizontalDivider(color = NavColors.divider, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(8.dp))

            DrawerMenuItem(
                text = "History Pays",
                iconRes = Res.drawable.ic_baseline_payment_24,
                onClick = onSkuListClick
            )
            DrawerMenuItem(
                text = "PIP",
                iconRes = Res.drawable.ic_baseline_camera_alt_24,
                onClick = onPipClick
            )
            DrawerMenuItem(
                text = "Settings",
                iconRes = Res.drawable.ic_baseline_settings_24,
                onClick = onSettingsClick
            )
        }
    }
}

// ── Drawer Header ─────────────────────────────────────────────────────────────

@Composable
private fun DrawerHeader(
    displayName: String,
    email: String,
    initials: String,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavColors.primaryDark)
            .padding(top = 24.dp, bottom = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ── Avatar — initials in brand green circle ────────────────────
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(NavColors.green),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── Logout button — door icon ──────────────────────────────────
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onSignOutClick() },
                shape = RoundedCornerShape(12.dp),
                color = Color.White.copy(alpha = 0.08f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_outline_sensor_door),
                        contentDescription = "Sign out",
                        tint = Color.White.copy(alpha = 0.75f),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // ── User name ──────────────────────────────────────────────────────
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = displayName,
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // ── Email ──────────────────────────────────────────────────────────
        if (email.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
                text = email,
                color = Color.White.copy(alpha = 0.55f),
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ── Drawer Menu Item ──────────────────────────────────────────────────────────

@Composable
private fun DrawerMenuItem(
    text: String,
    iconRes: org.jetbrains.compose.resources.DrawableResource,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = text,
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White.copy(alpha = 0.85f)),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 15.sp
        )
    }
}

// ── Top Bar ───────────────────────────────────────────────────────────────���───

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
            containerColor = NavColors.primary
        )
    )
}

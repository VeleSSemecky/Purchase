package com.veles.purchase.presentation.presentation.mvvm.purchase.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.skydoves.landscapist.glide.GlideImage
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.base.mvvm.fragment.MenuItemSelected
import com.veles.purchase.presentation.compose.CircularCenterProgressIndicator
import com.veles.purchase.presentation.compose.IconSquare
import com.veles.purchase.presentation.databinding.NavHostFragmentBinding
import com.veles.purchase.presentation.model.drawer.DrawerItem
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.mvvm.pip.PIP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NavigationFragment : BaseFragment(), MenuItemSelected {

    private val viewModel: NavigationViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        if (viewModel.isNeedLogin()) {
            findNavController().navigate(
                NavigationFragmentDirections.fragmentLogin()
            )
        }
        return inflater.inflate(
            R.layout.compose_view,
            container,
            false
        ).apply {
            findViewById<ComposeView>(R.id.composeView).setContent {
                ComposeContent()
            }
        }
    }

    @Preview
    @Composable
    fun ComposeContent() {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ToolBar(scaffoldState, scope)
            },
            floatingActionButton = {
            },
            bottomBar = {
            },
            drawerContent = {
                DrawerContent(scaffoldState, scope)
            },
            drawerBackgroundColor = Color.Transparent,
            drawerContentColor = Color.Transparent,
            drawerScrimColor = Color.Transparent,
            drawerElevation = 0.dp,
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = true,
            content = {
                AndroidViewBinding(NavHostFragmentBinding::inflate, Modifier.padding(it))
            },
            backgroundColor = Color.Black
        )
    }

    @Composable
    fun DrawerContent(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .background(Colors.colorPrimaryDark)
        ) {
            DrawerHeader()
            DrawerItems(scaffoldState, scope)
        }
    }

    @Composable
    fun DrawerHeader() {
        val user by viewModel.flowUserPurchaseModel.collectAsState()
        Column {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (IconUser, IconExit) = createRefs()
                GlideImage(
                    modifier = Modifier
                        .constrainAs(IconUser) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                        .padding(
                            horizontal = 24.dp,
                            vertical = 12.dp
                        )
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds,
                    imageModel = user.photoUrl?.ifEmpty { R.mipmap.ic_launcher_round },
                    loading = {
                        CircularCenterProgressIndicator()
                    },
                    contentDescription = null
                )
                GlideImage(
                    modifier = Modifier
                        .clickable {
                            viewModel.onSignOutGoogleSignInClient()
                            findNavController().navigate(
                                NavigationFragmentDirections.fragmentLogin()
                            )
                        }
                        .constrainAs(IconExit) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                        .padding(24.dp)
                        .size(24.dp),
                    imageModel = R.drawable.ic_outline_sensor_door
                )
            }
            Text(
                modifier = Modifier.padding(
                    horizontal = 24.dp
                ),
                text = user.displayName ?: "-",
                color = Color.White
            )
            Text(
                modifier = Modifier.padding(
                    horizontal = 24.dp
                ),
                text = user.email ?: "-",
                color = Color.Gray
            )
        }
    }

    @Composable
    fun DrawerItems(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        DrawerItem.values().forEach {
            Box(
                modifier = Modifier
                    .clickable {
                        when (it) {
                            DrawerItem.HISTORY -> findNavController().navigate(
                                NavigationFragmentDirections.fragmentHistory()
                            )
                            DrawerItem.SKU_LIST -> findNavController().navigate(
                                NavigationFragmentDirections.fragmentSkuList()
                            )
                            DrawerItem.PIP -> startActivity(
                                Intent(
                                    requireActivity(),
                                    PIP::class.java
                                )
                            )
                            DrawerItem.SETTING -> findNavController().navigate(
                                NavigationFragmentDirections.fragmentSettingPurchaseCompose()
                            )
                        }
                        scope.launch { scaffoldState.drawerState.currentValue }
                    }
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth()
            ) {
                DrawerItem(it)
            }
        }
    }

    @Composable
    fun DrawerItem(drawerItem: DrawerItem) {
        Row(modifier = Modifier) {
            GlideImage(
                modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                contentScale = ContentScale.FillBounds,
                imageModel = drawerItem.resImage,
                colorFilter = tint(Color.White)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = stringResource(drawerItem.resText),
                color = Color.White
            )
        }
    }

    @Composable
    fun ToolBar(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        TopAppBar(
            contentPadding = PaddingValues(0.dp),
            content = {
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (IconBack, TextTitle) = createRefs()
                    IconSquare(
                        id = R.drawable.ic_baseline_menu,
                        onClick = {
                            scope.launch { scaffoldState.drawerState.open() }
                        },
                        modifier = Modifier
                            .constrainAs(IconBack) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                    Text(
                        text = "Collection List",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .constrainAs(TextTitle) {
                                start.linkTo(parent.start, margin = 24.dp)
                                end.linkTo(parent.end, margin = 24.dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            },
            elevation = 0.dp,
            backgroundColor = Colors.colorPrimary
        )
    }

    override fun onMenuItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(findNavController())
}

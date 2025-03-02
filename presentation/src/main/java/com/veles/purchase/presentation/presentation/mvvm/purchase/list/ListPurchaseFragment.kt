package com.veles.purchase.presentation.presentation.mvvm.purchase.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.fragment.app.viewModels
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.DismissDirection
import com.veles.purchase.presentation.compose.DismissValue
import com.veles.purchase.presentation.compose.FractionalThreshold
import com.veles.purchase.presentation.compose.SwipeToDismiss
import com.veles.purchase.presentation.compose.rememberDismissState
import com.veles.purchase.presentation.compose.search.SearchTopAppBar
import com.veles.purchase.presentation.compose.search.SearchWidgetState
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.compose.ItemPurchaseState
import com.veles.purchase.presentation.model.setting.toShape
import com.veles.purchase.presentation.model.sort.SortPurchase
import com.veles.purchase.presentation.model.sort.toPurchaseComparator
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.textStyle1

class ListPurchaseFragment : BaseFragment() {

    private val viewModel: ListPurchaseViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
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

    @Composable
    fun ComposeContent() {
        Scaffold(
            topBar = {
                ToolBar()
            },
            floatingActionButton = {
            },
            bottomBar = {
            },
            floatingActionButtonPosition = FabPosition.End,
//            isFloatingActionButtonDocked = true,
            content = { innerPadding ->
                Content(innerPadding)
            },
            containerColor = Color.Black
        )
        Progress()
    }

    @Composable
    fun Progress() {
        val progress = viewModel.flowProgress.collectAsState()
        if (progress.value != Progress.Start) return
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.progress)
                .clickable(false) {
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Colors.gr)
        }
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ToolBar(
        viewModel: ListPurchaseViewModel = viewModel()
    ) {
        SearchTopAppBar(
            searchTextState = viewModel.flowSearchText.collectAsState().value,
            onTextChange = {
                viewModel.updateSearchText(it)
            },
            navigationIcon = {
                IconButton(
                    onClick = { viewModel.onBackClicked() },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            },
            title = {
                val collectionPurchaseName by viewModel.flowCollectionPurchase.collectAsState()
                Text(
                    text = collectionPurchaseName.name,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = { state ->
                IconButton(
                    onClick = { state.value = SearchWidgetState.OPENED },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { viewModel.onSettingsClicked() },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_settings_24),
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        )
    }

    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues()
    ) = Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues = paddingValues)
    ) {
        SortPurchase()

        val purchaseModels by viewModel.flowListPurchaseModels.collectAsState()
        val sortPurchase by viewModel.flowSortPurchase.collectAsState()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(1f)
        ) {
            itemsIndexed(
                purchaseModels.sortedWith(sortPurchase.toPurchaseComparator())
            ) { _, item ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                    dismissState.isDismissed(DismissDirection.StartToEnd)
                ) {
                    LaunchedEffect(key1 = this@ListPurchaseFragment, block = {
                        viewModel.apiFirebaseRemoveRepository(item)
                        dismissState.snapTo(DismissValue.Default)
                    })
                }
                Modifier
                    .fillMaxWidth()
                SwipeToDismiss(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(fadeInSpec = null, fadeOutSpec = null),
                    state = dismissState,
                    background = {},
                    dismissThresholds = { FractionalThreshold(0.7f) }
                ) {
                    val elevation =
                        animateDpAsState(
                            if (dismissState.dismissDirection == null) 0.dp else 4.dp
                        ).value
                    val state = ItemPurchaseState(
                        elevation = elevation,
                        item = item,
                        flowPurchaseSetting = viewModel.flowPurchaseSetting,
                        onItemClicked = { item -> viewModel.onItemClicked(item) },
                        onLongClicked = { item -> viewModel.onLongClicked(item) },
                        onChecked = { item -> viewModel.onChecked(item) }
                    )
                    ItemPurchase(state = state)
                }
            }
        }
        CreatePurchase()
    }

    @Composable
    fun SortPurchase() {
        val state = viewModel.flowSortPurchase.collectAsState()
        Text(
            text = getString(
                when (state.value) {
                    SortPurchase.SORTING_A_Z -> R.string.sorting_a_z
                    SortPurchase.SORTING_Z_A -> R.string.sorting_z_a
                    SortPurchase.SORTING_DATA_NEW -> R.string.sorting_data_new
                    SortPurchase.SORTING_DATA_OLD -> R.string.sorting_data_old
                    SortPurchase.SORTING_CHECK -> R.string.sorting_check
                    SortPurchase.SORTING_UNCHECK -> R.string.sorting_uncheck
                }
            ),
            color = Colors.gr,
            modifier = Modifier
                .clickable {
                    viewModel.onSortClicked()
                }
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun CreatePurchase() {
        val createTextState by viewModel.flowNewNamePurchase.collectAsState()
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            value = createTextState,
            onValueChange = {
                viewModel.onNewNamePurchaseChanged(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(0.60f),
                    text = getString(R.string.name_purchase),
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            singleLine = true,
            trailingIcon = {
                Row {
                    IconButton(
                        onClick = {
                            if (createTextState.isEmpty()) return@IconButton
                            viewModel.onNewNamePurchaseChanged()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = Color.White,
                            modifier = Modifier.alpha(
                                if (createTextState.isEmpty()) 0.toFloat() else 1.toFloat()
                            )
                        )
                    }
                    IconButton(
                        onClick = {
                            if (createTextState.isEmpty()) return@IconButton
                            viewModel.insertAdd(createTextState)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done Icon",
                            tint = Color.White,
                            modifier = Modifier.alpha(
                                if (createTextState.isEmpty()) 0.toFloat() else 1.toFloat()
                            )
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (createTextState.isEmpty()) return@KeyboardActions
                    viewModel.insertAdd(createTextState)
                }
            ),

//            private object HighContrastContentAlpha {
//                const val high: Float = 1.00f
//                const val medium: Float = 0.74f
//                const val disabled: Float = 0.38f
//            }
//
//                    /**
//                     * Alpha levels for low luminance content in light theme, or high luminance content in dark theme.
//                     *
//                     * This content will typically be placed on grayscale surfaces, so the contrast here can be lower
//                     * without sacrificing accessibility and legibility.
//                     *
//                     * These levels are typically used for body text on the main surface (white in light theme, grey
//                     * in dark theme) and text / iconography in surface colored tabs / bottom navigation / etc.
//                     */
//                    private object LowContrastContentAlpha {
//                const val high: Float = 0.87f
//                const val medium: Float = 0.60f
//                const val disabled: Float = 0.38f
//            }

            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = 0.60f),
                focusedIndicatorColor = Colors.gr.copy(alpha = 0.87f),
                focusedLabelColor = Colors.gr.copy(alpha = 0.87f)
            )
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ItemPurchase(
        state: ItemPurchaseState = ItemPurchaseState()
    ) {
        val item = state.item
        val purchaseSetting by state.flowPurchaseSetting.collectAsState()
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = Colors.colorAccent
            ),
            shape = purchaseSetting.toShape(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = state.elevation
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .combinedClickable(
                    onClick = { state.onItemClicked(item) },
                    onLongClick = { state.onLongClicked(item) }
                )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                val (
                    referenceIconPhoto,
                    referenceTextTitle,
                    referenceTextDescription,
                    referenceIconCheck
                ) = createRefs()
                createVerticalChain(referenceTextTitle, referenceTextDescription, chainStyle = ChainStyle.Packed)
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .constrainAs(referenceIconPhoto) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    if (purchaseSetting.isImage) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                            painter = painterResource(
                                if (item.listImage.isNotEmpty()) {
                                    R.drawable.image
                                } else {
                                    R.drawable.no_image
                                }
                            ),
                            contentDescription = "Is Image",
                            tint = Colors.gr
                        )
                    }
                }
                Text(
                    text = item.text,
                    fontSize = 18.sp,
                    style = textStyle1(),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .constrainAs(referenceTextTitle) {
                            start.linkTo(referenceIconPhoto.end)
                            end.linkTo(referenceIconCheck.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(referenceTextDescription.top)
                            width = Dimension.fillToConstraints
                        }
                )

                Text(
                    text = item.count,
                    fontSize = 14.sp,
                    style = textStyle1(),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .constrainAs(referenceTextDescription) {
                            start.linkTo(referenceIconPhoto.end)
                            end.linkTo(referenceIconCheck.start)
                            top.linkTo(referenceTextDescription.bottom)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                            visibility = if (item.count.isNotEmpty()) Visibility.Visible else Visibility.Gone
                        }
                )

                Box(
                    modifier = Modifier
                        .clickable {
                            state.onChecked(item)
                        }
                        .constrainAs(referenceIconCheck) {
                            start.linkTo(referenceTextTitle.end)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Checkbox(
                        checked = item.check,
                        onCheckedChange = {
                            state.onChecked(item)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Colors.gr,
                            uncheckedColor = Colors.gr,
                            checkmarkColor = Color.Black
                        )
                    )
                }
            }
        }
    }
}

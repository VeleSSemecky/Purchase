package com.veles.purchase.presentation.presentation.mvvm.purchase.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.fragment.app.viewModels
import com.veles.purchase.domain.model.purchase.PurchaseModel
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
import com.veles.purchase.presentation.model.purchase.PurchaseModelUI
import com.veles.purchase.presentation.model.purchase.compose.core.ProgressState
import com.veles.purchase.presentation.model.purchase.compose.list.ContentState
import com.veles.purchase.presentation.model.purchase.compose.list.CreatePurchaseState
import com.veles.purchase.presentation.model.purchase.compose.list.ItemPurchaseState
import com.veles.purchase.presentation.model.purchase.compose.list.SortPurchaseState
import com.veles.purchase.presentation.model.purchase.compose.list.ToolBarState
import com.veles.purchase.presentation.model.purchase.toPurchaseModel
import com.veles.purchase.presentation.model.purchase.toPurchaseModelUI
import com.veles.purchase.presentation.model.setting.toShape
import com.veles.purchase.presentation.model.sort.SortPurchase
import com.veles.purchase.presentation.model.sort.toPurchaseComparator
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.textStyle1
import com.veles.purchase.presentation.presentation.compose.textStyle2

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
                val state = ContentState(
                    flowListPurchaseModels = viewModel.flowListPurchaseModels,
                    apiFirebaseRemoveRepository = { item -> viewModel.apiFirebaseRemoveRepository(item) },
                    itemPurchaseState = ItemPurchaseState(
                        flowPurchaseSetting = viewModel.flowPurchaseSetting,
                        onItemClicked = { item -> viewModel.onItemClicked(item) },
                        onLongClicked = { item -> viewModel.onLongClicked(item) },
                        onChecked = { item -> viewModel.onChecked(item) }
                    ),
                    createPurchaseState = CreatePurchaseState(
                        flowNewNamePurchase = viewModel.flowNewNamePurchase,
                        onNewNamePurchaseChanged = { item -> viewModel.onNewNamePurchaseChanged(item) },
                        insertAdd = { item -> viewModel.insertAdd(item) }
                    ),
                    sortPurchaseState = SortPurchaseState(
                        flowSortPurchase = viewModel.flowSortPurchase,
                        onSortClicked = { viewModel.onSortClicked() }
                    ),
                    progressState = ProgressState(
                        flowProgress = viewModel.flowProgress
                    ),
                    toolBarState = ToolBarState(
                        flowSearchText = viewModel.flowSearchText,
                        flowCollectionPurchase = viewModel.flowCollectionPurchase,
                        updateSearchText = { item -> viewModel.updateSearchText(item) },
                        onBackClicked = { viewModel.onBackClicked() },
                        onSettingsClicked = { viewModel.onSettingsClicked() }
                    )
                )
                ComposeContent(state)
            }
        }
    }

    @Composable
    fun ComposeContent(state: ContentState = ContentState.PREVIEW_STATE) {
        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            topBar = {
                ToolBar(state.toolBarState)
            },
            floatingActionButton = {
            },
            bottomBar = {
            },
            floatingActionButtonPosition = FabPosition.End,
            content = { innerPadding ->
                Content(innerPadding, state = state)
            },
            containerColor = Color.Black
        )
        Progress(state.progressState)
    }

    @Composable
    fun Progress(state: ProgressState) {
        val progress = state.flowProgress.collectAsState()
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
        state: ToolBarState = ToolBarState.PREVIEW_STATE
    ) {
        val searchText by state.flowSearchText.collectAsState()
        SearchTopAppBar(
            searchTextState = searchText,
            onTextChange = {
                state.updateSearchText(it)
            },
            navigationIcon = {
                IconButton(
                    onClick = { state.onBackClicked() },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            },
            title = {
                val collectionPurchaseName by state.flowCollectionPurchase.collectAsState()
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
            actions = { searchWidgetState ->
                IconButton(
                    onClick = { searchWidgetState.value = SearchWidgetState.OPENED },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { state.onSettingsClicked() },
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

    @Preview
    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues(),
        state: ContentState = ContentState.PREVIEW_STATE
    ) = Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues = paddingValues)
    ) {
        SortPurchase(state.sortPurchaseState)

        val purchaseModels by state.flowListPurchaseModels.collectAsState()
        val sortPurchase by state.sortPurchaseState.flowSortPurchase.collectAsState()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f),
        ) {
            items(
                items = purchaseModels.sortedWith(sortPurchase.toPurchaseComparator()).map {
                    it.toPurchaseModelUI()
                },
                key = { item -> item }
            ) { item ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                    dismissState.isDismissed(DismissDirection.StartToEnd)
                ) {
                    LaunchedEffect(key1 = this@ListPurchaseFragment, block = {
                        state.apiFirebaseRemoveRepository(item.toPurchaseModel())
                        dismissState.snapTo(DismissValue.Default)
                    })
                }
                Modifier
                    .fillMaxWidth()
                SwipeToDismiss(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    state = dismissState,
                    background = {},
                    dismissThresholds = { FractionalThreshold(0.7f) }
                ) {
                    val elevation =
                        animateDpAsState(
                            if (dismissState.dismissDirection == null) 0.dp else 4.dp
                        ).value

                    ItemPurchase(
                        elevation = elevation,
                        item = item,
                        state = state.itemPurchaseState
                    )
                }
            }
        }
        CreatePurchase(state = state.createPurchaseState)
    }

    @Composable
    fun SortPurchase(
        state: SortPurchaseState
    ) {
        val sortPurchase = state.flowSortPurchase.collectAsState()
        Text(
            text = LocalContext.current.getString(
                when (sortPurchase.value) {
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
                    state.onSortClicked()
                }
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun CreatePurchase(
        state: CreatePurchaseState = CreatePurchaseState.PREVIEW_STATE
    ) {
        val createTextState by state.flowNewNamePurchase.collectAsState()

        val label = @Composable {
            Text(
                modifier = Modifier
                    .alpha(0.60f),
                text = "Search or create purchase",
                color = Color.White.copy(alpha = 0.87f)
            )
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            value = createTextState,
            onValueChange = {
                state.onNewNamePurchaseChanged(it)
            },
            label = if (createTextState.isNotEmpty()) label else null,
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(0.60f),
                    text = LocalContext.current.getString(R.string.name_purchase),
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
                            state.onNewNamePurchaseChanged("")
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
                            state.insertAdd(createTextState)
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
                    state.insertAdd(createTextState)
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

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ItemPurchase(
        elevation: Dp = 4.dp,
        item: PurchaseModelUI = PurchaseModel.TEST.toPurchaseModelUI(),
        state: ItemPurchaseState = ItemPurchaseState.PREVIEW_STATE
    ) {
        val purchaseSetting by state.flowPurchaseSetting.collectAsState()
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = Colors.colorAccent
            ),
            shape = purchaseSetting.toShape(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = elevation
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .combinedClickable(
                    onClick = { state.onItemClicked(item.toPurchaseModel()) },
                    onLongClick = { state.onLongClicked(item.toPurchaseModel()) }
                )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                val (
                    referenceTextTitle,
                    referenceTextDescription,
                    referenceIconCheck,
                    referenceChipCategory,
                ) = createRefs()
                createVerticalChain(referenceTextTitle, referenceTextDescription, referenceChipCategory, chainStyle = ChainStyle.Packed)

                Text(
                    text = item.text,
                    fontSize = 18.sp,
                    style = textStyle1(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .constrainAs(referenceTextTitle) {
                            start.linkTo(parent.start)
                            end.linkTo(referenceIconCheck.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(referenceTextDescription.top)
                            width = Dimension.fillToConstraints
                        }
                )

                Text(
                    text = item.count,
                    fontSize = 14.sp,
                    style = textStyle2(),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .constrainAs(referenceTextDescription) {
                            start.linkTo(parent.start)
                            end.linkTo(referenceIconCheck.start)
                            top.linkTo(referenceTextDescription.bottom)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                            visibility = if (item.count.isNotEmpty()) Visibility.Visible else Visibility.Gone
                        }
                )

                FlowRow(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp
                        )
                        .constrainAs(referenceChipCategory) {
                            start.linkTo(parent.start)
                            end.linkTo(referenceIconCheck.start)
                            top.linkTo(referenceChipCategory.bottom)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.wrapContent
                            horizontalBias = 0f
                        },
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryChip(item = item.toPurchaseModel())
                    PhotoChip(item = item.toPurchaseModel())
                }

                Box(
                    modifier = Modifier
                        .clickable {
                            state.onChecked(item.toPurchaseModel())
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
                            state.onChecked(item.toPurchaseModel())
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

    @Preview
    @Composable
    fun PhotoChip(
        item: PurchaseModel = PurchaseModel.TEST,
    ) {
        if (item.listImage.isEmpty()) return
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .background(
                    color = Colors.gr.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .height(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 2.dp)
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

    @Preview
    @Composable
    fun CategoryChip(
        item: PurchaseModel = PurchaseModel.TEST,
    ) {
        if (item.purchaseCategoryModel == null) return
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .background(
                    color = Colors.gr.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .height(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.purchaseCategoryModel?.name ?: "Uncategorized",
                fontSize = 12.sp,
                style = TextStyle(
                    color = Colors.gr,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            )
        }
    }

    @Preview
    @Composable
    fun FilteringDialog(
        selectedCategory: String = "Uncategorized",
        onCategorySelected: (String) -> Unit = {},
        onDismiss: () -> Unit = {}
    ) {
        val categories = listOf(
            "Uncategorized", "Meat and fish", "Grocery", "Oils",
            "Dairy and eggs", "Fruits and vegetables", "Conservation", "Seasonings"
        )

        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text(text = "Filtering", style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Column {
                    Text(text = "Find the product category you need.", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    categories.forEach { category ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = category == selectedCategory,
                                    onClick = { onCategorySelected(category) },
                                    role = Role.RadioButton
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = category,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            RadioButton(
                                selected = category == selectedCategory,
                                onClick = { onCategorySelected(category) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "Confirm", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

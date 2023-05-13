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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.DismissDirection
import com.veles.purchase.presentation.compose.DismissValue
import com.veles.purchase.presentation.compose.FractionalThreshold
import com.veles.purchase.presentation.compose.IconSquare
import com.veles.purchase.presentation.compose.SwipeToDismiss
import com.veles.purchase.presentation.compose.rememberDismissState
import com.veles.purchase.presentation.compose.search.SearchTopAppBar
import com.veles.purchase.presentation.compose.search.SearchWidgetState
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.setting.toShape
import com.veles.purchase.presentation.model.sort.SortPurchase
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
            isFloatingActionButtonDocked = true,
            content = {
                Content(it)
            },
            backgroundColor = Color.Black
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
            CircularProgressIndicator(
                color = Colors.gr
            )
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
            content = { state ->
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (
                        IconBack,
                        TextTitle,
                        IconSearch,
                        IconSettings,
                        IconLater
                    ) = createRefs()
                    IconSquare(
                        id = R.drawable.ic_baseline_arrow_back_24,
                        modifier = Modifier
                            .constrainAs(IconBack) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        onClick = {
                            viewModel.onBackClicked()
                        },
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                    val collectionPurchaseName by viewModel.flowCollectionPurchase.collectAsState()
                    Text(
                        text = collectionPurchaseName.name,
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .constrainAs(TextTitle) {
                                start.linkTo(IconBack.end, margin = 8.dp)
                                end.linkTo(IconSearch.start, margin = 8.dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                    IconSquare(
                        id = R.drawable.ic_baseline_search_24,
                        onClick = {
                            state.value = SearchWidgetState.OPENED
                        },
                        modifier = Modifier
                            .constrainAs(IconSearch) {
                                end.linkTo(IconLater.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        tint = Colors.gr,
                        contentDescription = "Search Icon"
                    )
                    IconSquare(
                        id = R.drawable.ic_later,
                        modifier = Modifier
                            .constrainAs(IconLater) {
                                end.linkTo(IconSettings.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        onClick = {
                            viewModel.onSettingsClicked()
                        },
                        contentDescription = "Setting Icon",
                        tint = Colors.gr
                    )
                    IconSquare(
                        id = R.drawable.ic_baseline_settings_24,
                        modifier = Modifier
                            .constrainAs(IconSettings) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        onClick = {
                            viewModel.onSettingsClicked()
                        },
                        contentDescription = "Setting Icon",
                        tint = Colors.gr
                    )
                }
            }
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                purchaseModels
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
                SwipeToDismiss(
                    modifier = Modifier.fillMaxWidth().animateItemPlacement(),
                    state = dismissState,
                    background = {},
                    dismissThresholds = { FractionalThreshold(0.7f) }
                ) {
                    val elevation =
                        animateDpAsState(
                            if (dismissState.dismissDirection == null) 0.dp else 4.dp
                        ).value
                    ItemPurchase(elevation, item)
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
                .fillMaxWidth(),
            value = createTextState,
            onValueChange = {
                viewModel.onNewNamePurchaseChanged(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = getString(R.string.name_purchase),
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
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
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                focusedIndicatorColor = Colors.gr.copy(alpha = ContentAlpha.high),
                focusedLabelColor = Colors.gr.copy(alpha = ContentAlpha.high)
            )
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ItemPurchase(
        elevation: Dp = 4.dp,
        item: PurchaseModel = PurchaseModel.TEST
    ) {
        val purchaseSetting by viewModel.flowPurchaseSetting.collectAsState()
        Card(
            backgroundColor = Colors.colorAccent,
            shape = purchaseSetting.toShape(),
            elevation = elevation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .combinedClickable(
                    onClick = { viewModel.onItemClicked(item) },
                    onLongClick = { viewModel.onLongClicked(item) }
                )
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (
                    IconPhoto,
                    TextTitle,
                    IconCheck
                ) = createRefs()

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .constrainAs(IconPhoto) {
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
                        .padding(8.dp)
                        .constrainAs(TextTitle) {
                            start.linkTo(IconPhoto.end)
                            end.linkTo(IconCheck.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                )

                Box(
                    modifier = Modifier
                        .clickable {
                            viewModel.onChecked(item)
                        }
                        .constrainAs(IconCheck) {
                            start.linkTo(TextTitle.end)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Checkbox(
                        checked = item.check,
                        onCheckedChange = {
                            viewModel.onChecked(item)
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

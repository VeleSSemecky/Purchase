package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.IconSquare
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.PurchaseCategoryModelUI
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.ConfirmLeaveDialogState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.ConfirmLeaveDialogType
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.ContentState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.CreateCategoryDialogState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.CreateCategoryDialogType
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.CreateCategoryState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.EditDialogState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.EditDialogType
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.FabState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.category.ToolBarState
import com.veles.purchase.presentation.model.purchase.compose.core.ProgressState
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.textStyle1

class CategoryFragment : BaseFragment() {

    private val viewModel: CategoryViewModel by viewModels { viewModelFactory }

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
                    flowListCategory = viewModel.flowListPurchaseCategoryModel,
                    toolBarState = ToolBarState(
                        onBackClicked = {
                            viewModel.onBackClicked()
                        },
                        onSaveClicked = {
                            viewModel.onSaveClicked()
                        }
                    ),
                    progressState = ProgressState(
                        flowProgress = viewModel.flowProgress
                    ),
                    createCategoryState = CreateCategoryState(
                        onItemClicked = { position, item ->
                            viewModel.onItemClicked(position, item)
                        },
                        onRemoveCategory = { item ->
                            viewModel.onRemoveCategory(item)
                        }
                    ),
                    fabState = FabState {
                        viewModel.onCreateCategoryDialogClicked()
                    },
                    editDialogState = EditDialogState(
                        flowEditDialogType = viewModel.flowEditDialogType,
                        onTextUpdated = { position, text ->
                            viewModel.onTextUpdated(position, text)
                        },
                        onEditDismissed = {
                            viewModel.onEditDismissed()
                        }
                    ),
                    createCategoryDialogState = CreateCategoryDialogState(
                        flowCreateCategoryDialogType = viewModel.flowCreateCategoryDialogType,
                        onCreateCategoryClicked = { text ->
                            viewModel.onCreateCategoryClicked(text)
                        },
                        onCreateCategoryDismiss = {
                            viewModel.onCreateCategoryDismiss()
                        }
                    ),
                    confirmLeaveDialogState = ConfirmLeaveDialogState(
                        flowConfirmLeaveDialogType = viewModel.flowConfirmLeaveDialogType,
                        onConfirmLeaveClicked = {
                            viewModel.onConfirmLeaveClicked()
                        },
                        onConfirmLeaveDismiss = {
                            viewModel.onConfirmLeaveDismiss()
                        }
                    )
                )
                ComposeContent(state)
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun ComposeContent(state: ContentState = ContentState.PREVIEW_STATE) {
        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            topBar = {
                ToolBar(state.toolBarState)
            },
            floatingActionButton = {
                FAB(state.fabState)
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
        EditDialog(state.editDialogState)
        CreateCategoryDialog(state.createCategoryDialogState)
        ConfirmLeaveDialog(state.confirmLeaveDialogState)
    }

    @Composable
    fun FAB(state: FabState) {
        FloatingActionButton(
            modifier = Modifier,
            onClick = {
                state.onCreateCategoryDialogClicked()
            }
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "FAB"
            )
        }
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
    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun ToolBar(
        toolBarState: ToolBarState = ToolBarState.PREVIEW_STATE
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = Colors.colorPrimary
            ),
            title = {
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (
                        referenceIconBack,
                        referenceTextTitle,
                        referenceIconSave
                    ) = createRefs()
                    IconSquare(
                        id = R.drawable.ic_baseline_arrow_back_24,
                        onClick = {
                            toolBarState.onBackClicked()
                        },
                        modifier = Modifier
                            .constrainAs(referenceIconBack) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                    Text(
                        text = "Category settings",
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .constrainAs(referenceTextTitle) {
                                start.linkTo(referenceIconBack.end, margin = 8.dp)
                                end.linkTo(referenceIconSave.start, margin = 8.dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                width = Dimension.fillToConstraints
                            }
                    )
                    IconSquare(
                        id = R.drawable.ic_done_black_24dp,
                        onClick = {
                            toolBarState.onSaveClicked()
                        },
                        modifier = Modifier
                            .constrainAs(referenceIconSave) {
                                end.linkTo(parent.end, margin = 16.dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
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
        val listCategory by state.flowListCategory.collectAsState()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                listCategory
            ) { position, item ->
                ItemCategory(
                    item = item,
                    position = position,
                    state = state.createCategoryState
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ItemCategory(
        elevation: Dp = 4.dp,
        item: PurchaseCategoryModelUI = PurchaseCategoryModelUI.EMPTY,
        position: Int = 0,
        state: CreateCategoryState = CreateCategoryState.PREVIEW_STATE
    ) {
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = Colors.colorAccent
            ),
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
                    onClick = { state.onItemClicked(position, item) },
                )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                val (
                    referenceTextTitle,
                    referenceIconPhoto,
                ) = createRefs()
                Text(
                    text = item.name,
                    fontSize = 18.sp,
                    style = textStyle1(),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .constrainAs(referenceTextTitle) {
                            start.linkTo(parent.start)
                            end.linkTo(referenceIconPhoto.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                )
                IconButton(
                    modifier = Modifier
                        .constrainAs(referenceIconPhoto) {
                            start.linkTo(referenceTextTitle.end)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    onClick = {
                        state.onRemoveCategory(item)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete_black_24dp),
                        contentDescription = "Remove Icon",
                        tint = Color.White,
                    )
                }
            }
        }
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun EditDialog(
        state: EditDialogState = EditDialogState.PREVIEW_STATE,
    ) {
        val editDialogType by state.flowEditDialogType.collectAsState()
        (editDialogType as? EditDialogType.Open)?.let { openEditDialogType ->
            var namePurchaseCategory by rememberSaveable { mutableStateOf(openEditDialogType.itemPurchaseCategoryModel.name) }
            AlertDialog(
                onDismissRequest = { state.onEditDismissed() },
                title = {
                    Text(text = "Edit Category", style = MaterialTheme.typography.titleLarge)
                },
                text = {
                    Column {
                        Text(text = "Find the product category you need.", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .imePadding(),
                            value = namePurchaseCategory,
                            onValueChange = { value ->
                                namePurchaseCategory = value
                            },
                            isError = namePurchaseCategory.isEmpty(),
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
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (namePurchaseCategory.isNotEmpty()) {
                                        state.onTextUpdated(
                                            openEditDialogType.position,
                                            namePurchaseCategory
                                        )
                                    }
                                }
                            ),

                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                cursorColor = Color.White.copy(alpha = 0.60f),
                                focusedIndicatorColor = Colors.gr.copy(alpha = 0.87f),
                                focusedLabelColor = Colors.gr.copy(alpha = 0.87f)
                            )
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        enabled = namePurchaseCategory.isNotEmpty(),
                        onClick = {
                            state.onTextUpdated(
                                openEditDialogType.position,
                                namePurchaseCategory
                            )
                        }
                    ) {
                        Text(text = "Confirm", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun CreateCategoryDialog(
        state: CreateCategoryDialogState = CreateCategoryDialogState.PREVIEW_STATE,
    ) {
        val createCategoryDialogType by state.flowCreateCategoryDialogType.collectAsState()
        (createCategoryDialogType as? CreateCategoryDialogType.Open)?.let { openEditDialogType ->
            var namePurchaseCategory by rememberSaveable { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = { state.onCreateCategoryDismiss() },
                title = {
                    Text(text = "Create Category", style = MaterialTheme.typography.titleLarge)
                },
                text = {
                    Column {
                        Text(text = "Find the product category you need.", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .imePadding(),
                            value = namePurchaseCategory,
                            onValueChange = { value ->
                                namePurchaseCategory = value
                            },
                            isError = namePurchaseCategory.isEmpty(),
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
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (namePurchaseCategory.isNotEmpty()) {
                                        state.onCreateCategoryClicked(namePurchaseCategory)
                                    }
                                }
                            ),

                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                cursorColor = Color.White.copy(alpha = 0.60f),
                                focusedIndicatorColor = Colors.gr.copy(alpha = 0.87f),
                                focusedLabelColor = Colors.gr.copy(alpha = 0.87f)
                            )
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        enabled = namePurchaseCategory.isNotEmpty(),
                        onClick = {
                            state.onCreateCategoryClicked(namePurchaseCategory)
                        }
                    ) {
                        Text(text = "Create", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ConfirmLeaveDialog(
        state: ConfirmLeaveDialogState = ConfirmLeaveDialogState.PREVIEW_STATE,
    ) {
        val createCategoryDialogType by state.flowConfirmLeaveDialogType.collectAsState()
        if (createCategoryDialogType !is ConfirmLeaveDialogType.Open) return
        AlertDialog(
            onDismissRequest = { state.onConfirmLeaveDismiss() },
            title = {
                Text(text = "Leave Category", style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Text(text = "Leave without save changes", style = MaterialTheme.typography.bodyMedium)
            },
            confirmButton = {
                TextButton(
                    onClick = { state.onConfirmLeaveClicked() }
                ) {
                    Text(text = "Confirm", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

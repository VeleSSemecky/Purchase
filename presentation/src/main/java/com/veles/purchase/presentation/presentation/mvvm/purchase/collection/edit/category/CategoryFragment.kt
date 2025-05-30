package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.veles.purchase.presentation.model.purchase.PurchaseCategoryModelUI
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
                val uiState by viewModel.uiState.collectAsState()
                ComposeContent(
                    uiState = uiState,
                    onBackClicked = { viewModel.onBackClicked() },
                    onSaveClicked = { viewModel.onSaveClicked() },
                    onItemClicked = { position, item -> viewModel.onItemClicked(position, item) },
                    onRemoveCategory = { viewModel.onRemoveCategory(it) },
                    onCreateCategoryDialogClicked = { viewModel.onCreateCategoryDialogClicked() },
                    onTextUpdated = { position, text -> viewModel.onTextUpdated(position, text) },
                    onDialogDismissed = { viewModel.onDialogDismissed() },
                    onCreateCategoryClicked = { viewModel.onCreateCategoryClicked(it) },
                    onConfirmLeaveClicked = { viewModel.onConfirmLeaveClicked() }
                )
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun ComposeContent(
        uiState: CategoryScreenState = CategoryScreenState.PREVIEW_STATE,
        onBackClicked: () -> Unit = {},
        onSaveClicked: () -> Unit = {},
        onItemClicked: (Int, PurchaseCategoryModelUI) -> Unit = { _, _ -> },
        onRemoveCategory: (PurchaseCategoryModelUI) -> Unit = {},
        onCreateCategoryDialogClicked: () -> Unit = {},
        onTextUpdated: (Int, String) -> Unit = { _, _ -> },
        onDialogDismissed: () -> Unit = {},
        onCreateCategoryClicked: (String) -> Unit = {},
        onConfirmLeaveClicked: () -> Unit = {}
    ) {
        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            topBar = {
                ToolBar(
                    onBackClicked = onBackClicked,
                    onSaveClicked = onSaveClicked
                )
            },
            floatingActionButton = {
                FAB(onCreateCategoryDialogClicked = onCreateCategoryDialogClicked)
            },
            bottomBar = {},
            floatingActionButtonPosition = FabPosition.End,
            content = { innerPadding ->
                Content(
                    paddingValues = innerPadding,
                    categories = uiState.categories,
                    onItemClicked = onItemClicked,
                    onRemoveCategory = onRemoveCategory
                )
            },
            containerColor = Color.Black
        )

        if (uiState.isLoading) {
            ProgressIndicator()
        }

        when (val dialogState = uiState.dialogState) {
            is DialogState.EditCategoryDialog -> EditDialog(
                position = dialogState.position,
                item = dialogState.item,
                onTextUpdated = onTextUpdated,
                onDismissed = onDialogDismissed
            )

            is DialogState.CreateCategoryDialog -> CreateCategoryDialog(
                onCreateCategoryClicked = onCreateCategoryClicked,
                onDismissed = onDialogDismissed
            )

            is DialogState.ConfirmLeaveDialog -> ConfirmLeaveDialog(
                onConfirmLeaveClicked = onConfirmLeaveClicked,
                onDismissed = onDialogDismissed
            )

            DialogState.NoDialog -> Unit
        }
    }

    @Composable
    fun FAB(onCreateCategoryDialogClicked: () -> Unit) {
        FloatingActionButton(
            modifier = Modifier,
            onClick = onCreateCategoryDialogClicked
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "FAB"
            )
        }
    }

    @Composable
    fun ProgressIndicator() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.progress)
                .clickable(false) {},
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Colors.gr)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ToolBar(
        onBackClicked: () -> Unit = {},
        onSaveClicked: () -> Unit = {}
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
                        onClick = onBackClicked,
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
                        onClick = onSaveClicked,
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
        categories: List<PurchaseCategoryModelUI> = CategoryScreenState.PREVIEW_STATE.categories,
        onItemClicked: (Int, PurchaseCategoryModelUI) -> Unit = { _, _ -> },
        onRemoveCategory: (PurchaseCategoryModelUI) -> Unit = {}
    ) = Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues = paddingValues)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                categories
            ) { position, item ->
                ItemCategory(
                    item = item,
                    position = position,
                    onItemClicked = onItemClicked,
                    onRemoveCategory = onRemoveCategory
                )
            }
        }
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ItemCategory(
        elevation: Dp = 4.dp,
        item: PurchaseCategoryModelUI = PurchaseCategoryModelUI.EMPTY,
        position: Int = 0,
        onItemClicked: (Int, PurchaseCategoryModelUI) -> Unit = { _, _ -> },
        onRemoveCategory: (PurchaseCategoryModelUI) -> Unit = {}
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
                    onClick = { onItemClicked(position, item) },
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
                    onClick = { onRemoveCategory(item) }
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

    @Composable
    fun CategoryNameTextField(
        value: String,
        onValueChange: (String) -> Unit,
        onImeActionDone: () -> Unit,
        modifier: Modifier = Modifier,
        isError: Boolean = value.isEmpty(),
        placeholderText: String
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .imePadding(),
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.60f),
                    text = placeholderText,
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
                onDone = { onImeActionDone() }
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

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun EditDialog(
        position: Int = 0,
        item: PurchaseCategoryModelUI = PurchaseCategoryModelUI.EMPTY,
        onTextUpdated: (Int, String) -> Unit = { _, _ -> },
        onDismissed: () -> Unit = {}
    ) {
        var namePurchaseCategory by rememberSaveable(item.id) { mutableStateOf(item.name) }
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = onDismissed,
            title = {
                Text(text = "Edit Category", style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Column {
                    Text(text = "Find the product category you need.", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    CategoryNameTextField(
                        value = namePurchaseCategory,
                        onValueChange = { namePurchaseCategory = it },
                        onImeActionDone = {
                            if (namePurchaseCategory.isNotEmpty()) {
                                onTextUpdated(position, namePurchaseCategory)
                            }
                        },
                        isError = namePurchaseCategory.isEmpty(),
                        placeholderText = context.getString(R.string.name_purchase)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = namePurchaseCategory.isNotEmpty(),
                    onClick = {
                        onTextUpdated(position, namePurchaseCategory)
                    }
                ) {
                    Text(text = "Confirm", fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun CreateCategoryDialog(
        onCreateCategoryClicked: (String) -> Unit = {},
        onDismissed: () -> Unit = {}
    ) {
        var namePurchaseCategory by rememberSaveable { mutableStateOf("") }
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = onDismissed,
            title = {
                Text(text = "Create Category", style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Column {
                    Text(text = "Find the product category you need.", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    CategoryNameTextField(
                        value = namePurchaseCategory,
                        onValueChange = { namePurchaseCategory = it },
                        onImeActionDone = {
                            if (namePurchaseCategory.isNotEmpty()) {
                                onCreateCategoryClicked(namePurchaseCategory)
                            }
                        },
                        isError = namePurchaseCategory.isEmpty(),
                        placeholderText = context.getString(R.string.name_purchase)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = namePurchaseCategory.isNotEmpty(),
                    onClick = {
                        onCreateCategoryClicked(namePurchaseCategory)
                    }
                ) {
                    Text(text = "Create", fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ConfirmLeaveDialog(
        onConfirmLeaveClicked: () -> Unit = {},
        onDismissed: () -> Unit = {}
    ) {
        AlertDialog(
            onDismissRequest = onDismissed,
            title = {
                Text(text = "Leave Category", style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Text(text = "Leave without save changes", style = MaterialTheme.typography.bodyMedium)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmLeaveClicked()
                        onDismissed() // Also dismiss after confirm
                    }
                ) {
                    Text(text = "Confirm", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissed) {
                    Text(text = "Cancel", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

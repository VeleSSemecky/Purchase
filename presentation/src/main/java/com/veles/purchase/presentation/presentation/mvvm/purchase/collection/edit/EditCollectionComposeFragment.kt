package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.CategoryState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.ComponentNameState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.ContentState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.ItemUserState
import com.veles.purchase.presentation.model.purchase.compose.collection.edit.ToolBarState
import com.veles.purchase.presentation.model.purchase.compose.core.ProgressState
import com.veles.purchase.presentation.model.user.UserCheckedUI
import com.veles.purchase.presentation.model.user.UserPurchaseModelUI
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.MyTheme
import com.veles.purchase.presentation.presentation.compose.textFieldColorsMaterial3
import com.veles.purchase.presentation.presentation.compose.textStyle
import com.veles.purchase.presentation.presentation.compose.textStyle1

class EditCollectionComposeFragment : BaseFragment() {

    private val viewModel: EditCollectionComposeViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.compose_view,
            container,
            false
        ).apply {
            findViewById<ComposeView>(R.id.composeView).setContent {
                val state = ContentState(
                    flowListUserChecked = viewModel.flowListUserChecked,
                    componentNameState = ComponentNameState(
                        flowCollectionName = viewModel.flowCollectionName,
                        setCollectionName = { name -> viewModel.setCollectionName(name) }
                    ),
                    itemUserState = ItemUserState(
                        onUpdateCheck = { index, item -> viewModel.onUpdateCheck(index, item) }
                    ),
                    toolBarState = ToolBarState {
                        viewModel.save()
                    },
                    progressState = ProgressState(
                        flowProgress = viewModel.flowProgress
                    ),
                    categoryState = CategoryState {
                        viewModel.onCategoryClicked()
                    }
                )
                MyTheme {
                    Scaffold(
                        topBar = {
                            ToolBar(state.toolBarState)
                        },
                        content = { innerPadding ->
                            Content(paddingValues = innerPadding, state = state)
                        },
                        floatingActionButtonPosition = FabPosition.End,
                        containerColor = Color.Black
                    )
                    Progress(state.progressState)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun ToolBar(
        state: ToolBarState = ToolBarState.PREVIEW_STATE
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = { findNavController().popBackStack() },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = "Localized description"
                    )
                }
            },
            title = {
                Text(
                    text = "Create Collection",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        state.save()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_done_black_24dp),
                        contentDescription = "Localized description"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = Colors.colorPrimary
            )
        )
    }

    @Preview
    @Composable
    fun Progress(
        state: ProgressState = ProgressState.PREVIEW_STATE
    ) {
        val progress by state.flowProgress.collectAsState()
        if (progress != Progress.Start) return
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.progress)
                .clickable(false) {
                }
        ) {
            CircularProgressIndicator(color = Colors.gr)
        }
    }

    @Preview
    @Composable
    fun ComponentName(
        state: ComponentNameState = ComponentNameState.PREVIEW_STATE
    ) {
        val text by state.flowCollectionName.collectAsState()
        OutlinedTextField(
            colors = textFieldColorsMaterial3(),
            textStyle = textStyle(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp
                ),
            isError = text.isError,
            value = text.model,
            onValueChange = {
                state.setCollectionName(it)
            },
            label = {
                Text(
                    text = "Title",
                    color = Color.White
                )
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
        Spacer(modifier = Modifier.padding(16.dp))
        ComponentName(state.componentNameState)
        Spacer(modifier = Modifier.padding(8.dp))
        Component(state.categoryState)
        Spacer(modifier = Modifier.padding(8.dp))

        val skuEntityList by state.flowListUserChecked.collectAsState()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                skuEntityList
            ) { index, item ->
                ItemUser(item, index, state.itemUserState)
            }
        }
    }

    @Preview
    @Composable
    fun ItemUser(
        item: UserCheckedUI = UserCheckedUI(userPurchase = UserPurchaseModelUI()),
        index: Int = 0,
        state: ItemUserState = ItemUserState.PREVIEW_STATE
    ) {
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = Colors.colorAccent
            ),
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .clickable {
                    state.onUpdateCheck(index, item)
                }

        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (
                    IconCheck,
                    TextName,
                    TextEmail
                ) = createRefs()
                Box(
                    modifier = Modifier
                        .clickable {
                            state.onUpdateCheck(index, item)
                        }
                        .padding(16.dp)
                        .constrainAs(IconCheck) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Checkbox(
                        checked = item.isCheck,
                        onCheckedChange = {
                            state.onUpdateCheck(index, item)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Colors.gr,
                            uncheckedColor = Colors.gr,
                            checkmarkColor = Color.Black
                        )
                    )
                }
                Text(
                    text = item.userPurchase.displayName ?: emptyString(),
                    fontSize = 18.sp,
                    style = textStyle1(),
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 8.dp
                        )
                        .constrainAs(TextName) {
                            start.linkTo(IconCheck.end)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(TextEmail.top)
                            width = Dimension.fillToConstraints
                        }
                )
                Text(
                    text = item.userPurchase.email ?: emptyString(),
                    fontSize = 18.sp,
                    style = textStyle1(),
                    modifier = Modifier
                        .padding(8.dp)
                        .constrainAs(TextEmail) {
                            start.linkTo(IconCheck.end)
                            end.linkTo(parent.end)
                            top.linkTo(TextName.bottom)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                )
            }
        }
    }


    @Preview
    @Composable
    fun Component(categoryState: CategoryState = CategoryState.PREVIEW_STATE) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 20.dp)
                .clickable {
                    categoryState.onCategoryClicked()
                },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = Colors.colorAccent
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(R.drawable.ic_category),
                    contentDescription = "Is Image",
                    tint = Colors.gr
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    text = "Category settings"
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(R.drawable.ic_navigate_next),
                    contentDescription = "Is Image",
                    tint = Colors.gr
                )
            }
        }
    }
}

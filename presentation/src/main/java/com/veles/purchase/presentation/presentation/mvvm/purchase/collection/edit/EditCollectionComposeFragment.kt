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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.IconSquare
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.user.UserCheckedUI
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.MyTheme
import com.veles.purchase.presentation.presentation.compose.textFieldColors
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
                MyTheme {
                    Scaffold(
                        topBar = {
                            ToolBar()
                        },
                        content = {
                            Content(it)
                        },
                        floatingActionButtonPosition = FabPosition.End,
                        isFloatingActionButtonDocked = true,
                        backgroundColor = Color.Black
                    )
                    Progress()
                }
            }
        }
    }

    @Composable
    fun ToolBar() {
        TopAppBar(
            contentPadding = PaddingValues(0.dp),
            content = {
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (
                        IconBack,
                        TextTitle,
                        IconSave
                    ) = createRefs()
                    IconSquare(
                        id = R.drawable.ic_baseline_arrow_back_24,
                        onClick = {
                            findNavController().popBackStack()
                        },
                        modifier = Modifier
                            .constrainAs(IconBack) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                    Text(
                        text = "Create Collection",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .constrainAs(TextTitle) {
                                start.linkTo(IconBack.end, margin = 8.dp)
                                end.linkTo(IconSave.start, margin = 8.dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                    IconSquare(
                        id = R.drawable.ic_done_black_24dp,
                        onClick = {
                            viewModel.save {
                                findNavController().popBackStack()
                            }
                        },
                        modifier = Modifier
                            .constrainAs(IconSave) {
                                end.linkTo(parent.end, margin = 16.dp)
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

    @Composable
    fun Progress() {
        val progress by viewModel.flowProgress.collectAsState()
        if (progress != Progress.Start) return
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.progress)
                .clickable(false) {
                }
        ) {
            CircularProgressIndicator(
                color = Colors.gr
            )
        }
    }

    @Composable
    fun ComponentName() {
        val text by viewModel.flowCollectionName.collectAsState()
        OutlinedTextField(
            colors = textFieldColors(),
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
                viewModel.setCollectionName(it)
            },
            label = {
                Text(
                    text = "Title",
                    color = Color.White
                )
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
        Spacer(modifier = Modifier.padding(16.dp))
        ComponentName()
        Spacer(modifier = Modifier.padding(8.dp))

        val skuEntityList by viewModel.flowListUserChecked.collectAsState()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                skuEntityList
            ) { index, item ->
                ItemUser(item, index)
            }
        }
    }

    @Composable
    fun ItemUser(
        item: UserCheckedUI,
        index: Int
    ) {
        Card(
            backgroundColor = Colors.colorAccent,
            shape = RoundedCornerShape(0.dp),
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .clickable {
                    viewModel.onUpdateCheck(index, item)
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
                            viewModel.onUpdateCheck(index, item)
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
                            viewModel.onUpdateCheck(index, item)
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
}

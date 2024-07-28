package com.veles.purchase.presentation.presentation.mvvm.purchase.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.skydoves.landscapist.glide.GlideImage
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.CircularCenterProgressIndicator
import com.veles.purchase.presentation.compose.IconSquare
import com.veles.purchase.presentation.data.result.RequestPermissionCameraContract
import com.veles.purchase.presentation.data.result.RequestUriContract
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.toPurchasePhotoModelUI
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.MyTheme
import com.veles.purchase.presentation.presentation.compose.shopping.edit.SkuEditFragmentDirections
import com.veles.purchase.presentation.presentation.compose.textFieldColorsMaterial3
import com.veles.purchase.presentation.presentation.compose.textStyle
import java.time.LocalDateTime
import java.time.ZoneOffset

class EditPurchaseFragment : BaseFragment() {

    private val viewModel: EditPurchaseViewModel by viewModels { viewModelFactory }

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
                        bottomBar = {
                            BottomBar()
                        },
                        containerColor = Color.Black
                    )
                    Progress()
                }
            }
        }
    }

    private val requestUriContract = registerForActivityResult(RequestUriContract()) {
        viewModel.setPurchasePhotoModel(it ?: return@registerForActivityResult)
    }

    private val requestPermissionCameraContract = registerForActivityResult(
        RequestPermissionCameraContract()
    ) {
        requestUriContract.launch(it)
    }

    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues()
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            ComponentName()
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentPrice()
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentComment()
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDate()
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentSwitch()
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentPhoto()
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }

    @Composable
    fun ComponentName(
        viewModel: EditPurchaseViewModel = viewModel(
            factory = viewModelFactory
        )
    ) {
        val text by viewModel.flowPurchaseName.collectAsState()
        OutlinedTextField(
            colors = textFieldColorsMaterial3(),
            textStyle = textStyle(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp
                ),
            isError = text.isBlank(),
            value = text,
            onValueChange = {
                viewModel.onTitleChange(it)
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
    fun ComponentPrice(
        viewModel: EditPurchaseViewModel = viewModel()
    ) {
        val price by viewModel.flowPurchasePrice.collectAsState()
        val currency by viewModel.flowPurchaseCurrency.collectAsState()
        OutlinedTextField(
            colors = textFieldColorsMaterial3(),
            textStyle = textStyle(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp
                ),
            value = price,
            maxLines = 1,
            trailingIcon = {
                Text(
                    modifier = Modifier
                        .clickable {
                            findNavController().navigate(
                                SkuEditFragmentDirections.fragmentCurrencyChoose(
                                    currency.currencyCode
                                )
                            )
                        }
                        .padding(
                            top = 10.dp,
                            bottom = 10.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                    text = currency.symbol,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            },
            onValueChange = { it ->
                if (it.count { it.toString() == "." } > 1 ||
                    it.substringAfter(".", emptyString()).count() > 2
                ) {
                    return@OutlinedTextField
                }
                if (it.isEmpty() || it.matches("[0123456789.]+".toRegex())) {
                    viewModel.onPriceChange(
                        it
                    )
                }
            },
            label = {
                Text(
                    "Price",
                    color = Color.White
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }

    @Composable
    fun ComponentComment(
        viewModel: EditPurchaseViewModel = viewModel(
            factory = viewModelFactory
        )
    ) {
        val text by viewModel.flowPurchaseComment.collectAsState()

        OutlinedTextField(
            colors = textFieldColorsMaterial3(),
            textStyle = textStyle(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp
                ),
            value = text,
            onValueChange = { viewModel.onCommentChange(it) },
            label = { Text("Comment", color = Color.White) }
        )
    }

    @Composable
    fun ComponentDate(
        viewModel: EditPurchaseViewModel = viewModel()
    ) {
        val data by viewModel.flowPurchaseLocalData.collectAsState()
        OutlinedTextField(
            colors = textFieldColorsMaterial3().copy(
                disabledIndicatorColor = Color.White.copy(alpha = 0.38f)
            ),
            textStyle = textStyle(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp
                )
                .clickable {
                    showDatePicker()
                },
            enabled = false,
            readOnly = true,
            value = "${data.dayOfMonth}.${data.month.ordinal}.${data.year}",
            onValueChange = {},
            label = { Text("Local Data", color = Color.White) }
        )
    }

    @Composable
    fun ComponentPhoto(
        viewModel: EditPurchaseViewModel = viewModel()
    ) {
        val purchasePhotoModelList by viewModel.flowPurchasePhotoModelList.collectAsState()

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                purchasePhotoModelList
//                    .filter { it.isNeedDelete.not() }
                    .map { it }
            ) { purchasePhotoModel ->
                Card(
                    border = BorderStroke(2.dp, Color.White.copy(alpha = 0.38f))
                ) {
                    GlideImage(
                        modifier = Modifier
                            .size(70.dp)
                            .clickable {
                                findNavController().navigate(
                                    EditPurchaseFragmentDirections.fragmentPhotoPurchaseCompose(
                                        purchasePhotoModel.toPurchasePhotoModelUI()
                                    )
                                )
                            },
                        contentScale = ContentScale.FillBounds,
                        imageModel = viewModel.apiDatabaseURL(purchasePhotoModel),
                        loading = {
                            CircularCenterProgressIndicator()
                        },
                        contentDescription = null
                    )
                }
            }
        }
    }

    @Composable
    fun ComponentSwitch(
        viewModel: EditPurchaseViewModel = viewModel()
    ) {
        val purchaseIsChecked by viewModel.flowPurchaseIsChecked.collectAsState()
        Row(
            modifier = Modifier
                .padding(PaddingValues(horizontal = 20.dp))
                .border(
                    width = 1.dp,
                    Color.White.copy(alpha = 0.38f),
                    shape = OutlinedTextFieldDefaults.shape
                )
                .padding(PaddingValues(horizontal = 16.dp))
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = TextFieldDefaults.MinHeight
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Checked",
                color = Color.White
            )
            Switch(
                modifier = Modifier,
                checked = purchaseIsChecked,
                onCheckedChange = { isChecked ->
                    viewModel.onCheckedChange(isChecked)
                }
            )
        }
    }

    @Composable
    fun Progress() {
        val progress = viewModel.flowProgress.collectAsState()
        if (progress.value != Progress.Start) return
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.progress)
                .clickable(false) {},
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Colors.gr
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ToolBar() {
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
                    text = "Add purchase",
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
                    onClick = { viewModel.onSaveClicked() },
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

    @Composable
    fun BottomBar() {
        BottomAppBar(
            contentPadding = PaddingValues(),
            containerColor = Colors.colorPrimaryDark.copy(alpha = 0.8.toFloat())
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (
                    IconPhoto,
                    IconCalendar
                ) = createRefs()
                IconSquare(
                    id = R.drawable.ic_baseline_add_a_photo_24,
                    onClick = {
                        requestPermissionCameraContract.launch()
                    },
                    modifier = Modifier
                        .constrainAs(IconPhoto) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
                IconSquare(
                    id = R.drawable.ic_baseline_calendar_today_24,
                    onClick = {
                        showDatePicker()
                    },
                    modifier = Modifier
                        .constrainAs(IconCalendar) {
                            end.linkTo(IconPhoto.start, margin = 8.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
    }

    private fun showDatePicker() {
        val picker = MaterialDatePicker.Builder
            .datePicker()
            .setSelection(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
            .build()
        picker.show(requireActivity().supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { data ->
            viewModel.setPurchaseLocalData(data)
        }
    }
}

package com.veles.purchase.presentation.presentation.mvvm.purchase.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.model.setting.ShapeType
import com.veles.purchase.domain.model.setting.SizeType
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.IconSquare
import com.veles.purchase.presentation.model.setting.toShape
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.MyTheme
import com.veles.purchase.presentation.presentation.compose.textStyle1

class SettingPurchaseComposeFragment : BaseFragment() {

    private val viewModel: SettingPurchaseComposeViewModel by viewModels { viewModelFactory }

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
                        containerColor = Color.Black
                    )
                }
            }
        }
    }

    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues()
    ) {
        Column(
            modifier = Modifier.padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ComponentShape()
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ToolBar() {
        TopAppBar(
//            contentPadding = PaddingValues(0.dp),
            title = {
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
                        text = "Setting Purchase",
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
                            viewModel.onSaveSettingsPurchaseChanged()
                        },
                        modifier = Modifier
                            .constrainAs(IconSave) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            },
//            elevation = 0.dp,
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = Colors.colorPrimary
            )
        )
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ComponentShape() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val settingsPurchase by viewModel.flowPurchaseSetting.collectAsState()

            Spacer(modifier = Modifier.padding(16.dp))
            ItemPurchase(purchaseSetting = settingsPurchase)
            Spacer(modifier = Modifier.padding(16.dp))

            ShapeType.entries.forEach {
                TypeShape(it, settingsPurchase)
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Corner(settingsPurchase)

            Spacer(modifier = Modifier.padding(16.dp))

            SizeType.entries.forEach {
                SizeType(it, settingsPurchase)
            }

            Spacer(modifier = Modifier.padding(16.dp))

            IsMaintainSymmetry(settingsPurchase)
            IsShowImage(settingsPurchase)

            Spacer(modifier = Modifier.padding(24.dp))
        }
    }

    @Composable
    fun Corner(purchaseSetting: PurchaseSetting) {
        when (purchaseSetting.isSymmetry) {
            true -> AllCorner()
            false -> SideCorner()
        }
    }

    @Composable
    fun IsMaintainSymmetry(purchaseSetting: PurchaseSetting) {
        Row(
            modifier = Modifier
                .selectable(
                    selected = purchaseSetting.isSymmetry,
                    onClick = {
                        viewModel.onIsSymmetryChanged(purchaseSetting.isSymmetry.not())
                    }
                )
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = purchaseSetting.isSymmetry,
                onCheckedChange = {
                    viewModel.onIsSymmetryChanged(it)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Colors.gr,
                    uncheckedColor = Colors.gr,
                    checkmarkColor = Color.Black
                )
            )
            Text(
                text = "Symmetry",
                fontSize = 18.sp,
                style = textStyle1()
            )
        }
    }

    @Composable
    fun IsShowImage(purchaseSetting: PurchaseSetting) {
        Row(
            modifier = Modifier
                .selectable(
                    selected = purchaseSetting.isImage,
                    onClick = {
                        viewModel.onIsShowImageChanged(purchaseSetting.isImage.not())
                    }
                )
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = purchaseSetting.isImage,
                onCheckedChange = {
                    viewModel.onIsShowImageChanged(it)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Colors.gr,
                    uncheckedColor = Colors.gr,
                    checkmarkColor = Color.Black
                )
            )
            Text(
                text = "Show image",
                fontSize = 18.sp,
                style = textStyle1()
            )
        }
    }

    @Composable
    fun SideCorner() {
        val sideCorner by viewModel.flowSideCorner.collectAsState()
        Column {
            SliderText(value = sideCorner.topStart) {
                viewModel.onSideCornerChanged(topStart = it)
            }
            SliderText(value = sideCorner.topEnd) {
                viewModel.onSideCornerChanged(topEnd = it)
            }
            SliderText(value = sideCorner.bottomStart) {
                viewModel.onSideCornerChanged(bottomStart = it)
            }
            SliderText(value = sideCorner.bottomEnd) {
                viewModel.onSideCornerChanged(bottomEnd = it)
            }
        }
    }

    @Composable
    fun AllCorner() {
        val allCorner by viewModel.flowAllCorner.collectAsState()
        SliderText(value = allCorner.size) {
            viewModel.onAllCornerChanged(size = it)
        }
    }

    @Composable
    fun SizeType(item: SizeType, purchaseSetting: PurchaseSetting) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = item == purchaseSetting.sizeType,
                    onClick = {
                        viewModel.onSizeTypeChanged(item)
                    }
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = item == purchaseSetting.sizeType,
                onClick = {
                    viewModel.onSizeTypeChanged(item)
                },
                colors = RadioButtonDefaults.colors().copy(
                    selectedColor = Colors.gr,
                    unselectedColor = Colors.gr,
                    disabledSelectedColor = Color.Black,
                    disabledUnselectedColor = Color.Black
                )
            )
            Text(
                text = item.toString(),
                fontSize = 18.sp,
                style = textStyle1()
            )
        }
    }

    @Composable
    fun SliderText(
        value: Float,
        onValueChange: (Float) -> Unit
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Slider(
                modifier = Modifier.weight(1f),
                value = value,
                onValueChange = { onValueChange(it) },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    thumbColor = Colors.gr,
                    activeTrackColor = Colors.gr
                )
            )
            Box {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(45.dp)
                        .align(Alignment.Center),
                    text = value.toInt().toString(),
                    fontSize = 18.sp,
                    style = textStyle1()
                )
            }
        }
    }

    @Composable
    fun TypeShape(item: ShapeType, purchaseSetting: PurchaseSetting) {
        Row(
            modifier = Modifier
                .selectable(
                    selected = item == purchaseSetting.shapeType,
                    onClick = {
                        viewModel.onTypeShapeChanged(item)
                    }
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            RadioButton(
                selected = item == purchaseSetting.shapeType,
                onClick = {
                    viewModel.onTypeShapeChanged(item)
                },
                colors = RadioButtonDefaults.colors().copy(
                    selectedColor = Colors.gr,
                    unselectedColor = Colors.gr,
                    disabledSelectedColor = Color.Black
                )
            )
            Text(
                textAlign = TextAlign.Center,
                text = item.toString(),
                fontSize = 18.sp,
                style = textStyle1()
            )
        }
    }

    @Composable
    fun ItemPurchase(
        purchaseSetting: PurchaseSetting,
        item: PurchaseModel = PurchaseModel.TEST
    ) {
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = Colors.colorAccent
            ),
            shape = purchaseSetting.toShape(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .clickable {
                }
        ) {
            ConstraintLayout {
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

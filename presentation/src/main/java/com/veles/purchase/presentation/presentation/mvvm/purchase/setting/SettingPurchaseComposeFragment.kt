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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.IconSquare
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
                        backgroundColor = Color.Black
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
//                            viewModel.save {
//                                findNavController().popBackStack()
//                            }
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
            elevation = 0.dp,
            backgroundColor = Colors.colorPrimary
        )
    }

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun ComponentShape() {
        Column {
            val (sliderPosition, onSliderPosition) = rememberSaveable { mutableStateOf(0f) }
            val radioOptions = listOf(TypeShape.CUT, TypeShape.ROUNDED)
            val (selectedOption, onOptionSelected) = rememberSaveable {
                mutableStateOf(radioOptions[0])
            }
            val radioOptionsSizeType = listOf(SizeType.DP, SizeType.PERCENT)
            val (selectedOptionSizeType, onOptionSelectedSizeType) = rememberSaveable {
                mutableStateOf(radioOptionsSizeType[0])
            }
            val (sliderPositionTopStart, onSliderPositionTopStart) = rememberSaveable {
                mutableStateOf(0f)
            }
            val (sliderPositionTopEnd, onSliderPositionTopEnd) = rememberSaveable {
                mutableStateOf(0f)
            }
            val (sliderPositionBottomStart, onSliderPositionBottomStart) = rememberSaveable {
                mutableStateOf(0f)
            }
            val (sliderPositionBottomEnd, onSliderPositionBottomEnd) = rememberSaveable {
                mutableStateOf(0f)
            }
            val (checkedIsMaintain, onOptionCheckedIsMaintain) = rememberSaveable {
                mutableStateOf(
                    false
                )
            }
            val (checkedIsImage, onOptionCheckedIsImage) = rememberSaveable { mutableStateOf(true) }
            Spacer(modifier = Modifier.padding(16.dp))
            ItemPurchase(
                settingsPurchase = SettingsPurchase(
                    sizeType = selectedOptionSizeType,
                    typeShape = selectedOption,
                    corners = when (checkedIsMaintain) {
                        false -> Corners.AllCorner(sliderPosition)
                        true -> Corners.SideCorner(
                            topStart = sliderPositionTopStart,
                            topEnd = sliderPositionTopEnd,
                            bottomEnd = sliderPositionBottomEnd,
                            bottomStart = sliderPositionBottomStart
                        )
                    },
                    isShowImage = checkedIsImage
                )
            )
            Spacer(modifier = Modifier.padding(16.dp))
            LazyColumn {
                items(radioOptions) { item ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (item == selectedOption),
                                onClick = {
                                    onOptionSelected(item)
                                }
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = (item == selectedOption),
                            onClick = { onOptionSelected(item) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Colors.gr,
                                unselectedColor = Colors.gr,
                                disabledColor = Color.Black
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
            }
            Spacer(modifier = Modifier.padding(16.dp))
            if (checkedIsMaintain) {
                Column {
                    SliderText(value = sliderPositionTopStart) { onSliderPositionTopStart(it) }
                    SliderText(value = sliderPositionTopEnd) { onSliderPositionTopEnd(it) }
                    SliderText(value = sliderPositionBottomStart) {
                        onSliderPositionBottomStart(it)
                    }
                    SliderText(value = sliderPositionBottomEnd) { onSliderPositionBottomEnd(it) }
                }
            } else {
                SliderText(value = sliderPosition) { onSliderPosition(it) }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            LazyColumn {
                items(radioOptionsSizeType) { item ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (item == selectedOptionSizeType),
                                onClick = {
                                    onOptionSelectedSizeType(item)
                                }
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (item == selectedOptionSizeType),
                            onClick = { onOptionSelectedSizeType(item) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Colors.gr,
                                unselectedColor = Colors.gr,
                                disabledColor = Color.Black
                            )
                        )
                        Text(
                            text = item.toString(),
                            fontSize = 18.sp,
                            style = textStyle1()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedIsMaintain,
                    onCheckedChange = {
                        onOptionCheckedIsMaintain(it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Colors.gr,
                        uncheckedColor = Colors.gr,
                        checkmarkColor = Color.Black
                    )
                )
                Text(
                    text = "Maintain symmetry",
                    fontSize = 18.sp,
                    style = textStyle1()
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedIsImage,
                    onCheckedChange = {
                        onOptionCheckedIsImage(it)
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
    fun ItemPurchase(
        settingsPurchase: SettingsPurchase = SettingsPurchase(),
        item: PurchaseModel = PurchaseModel.TEST
    ) {
        Card(
            backgroundColor = Colors.colorAccent,
            shape = settingsPurchase.toShape(),
            elevation = 4.dp,
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
                    if (settingsPurchase.isShowImage) {
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

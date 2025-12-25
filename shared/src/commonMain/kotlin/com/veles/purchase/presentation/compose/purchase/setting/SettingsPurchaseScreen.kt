package com.veles.purchase.presentation.compose.purchase.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.model.setting.ShapeType
import com.veles.purchase.domain.model.setting.SizeType
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.compose.textStyle1
import com.veles.purchase.presentation.mvvm.purchase.setting.SettingsPurchaseViewModel
import com.veles.purchase.presentation.mvvm.purchase.setting.CornerSetting
import org.koin.compose.viewmodel.koinViewModel

/**
 * Settings Screen for Purchase appearance
 *
 * Migrated from: /Users/yuriimelnyk/StudioProjects/Purchase/presentation/src/main/java/com/veles/purchase/presentation/presentation/mvvm/purchase/setting/SettingPurchaseComposeFragment.kt
 *
 * Original design:
 * - Dark theme (black background, white text)
 * - Custom colors (Colors.gr = #4ACFAC green accent)
 * - Radio buttons for Shape/Size type
 * - Checkboxes for Image/Symmetry
 * - Sliders with value display
 * - Preview card with sample purchase item
 *
 * Phase 2.3 - Full feature migration with original styling
 * FIXED: Now using custom components matching pattern exactly
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPurchaseScreen(
    viewModel: SettingsPurchaseViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val settings by viewModel.flowPurchaseSetting.collectAsState()
    val allCorner by viewModel.flowAllCorner.collectAsState()
    val sideCorner by viewModel.flowSideCorner.collectAsState()

    // Apply original dark theme
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Colors.colorPrimary,
            surface = Colors.surface,
            primaryContainer = Colors.gr,
            background = Color.Black
        )
    ) {
        Scaffold(
            topBar = {
                ToolBar(onNavigateBack, viewModel)
            },
            containerColor = Color.Black
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Preview Card - same as original ItemPurchase
                PreviewCard(settings)

                Spacer(modifier = Modifier.height(16.dp))

                // Shape Type - Radio buttons like original
                ShapeType.entries.forEach { shapeType ->
                    ShapeTypeRow(shapeType, settings, viewModel)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Corner Sliders
                if (settings.isSymmetry) {
                    AllCornerSlider(allCorner.topStart) {
                        viewModel.onAllCornerChanged(it)
                    }
                } else {
                    SideCornerSliders(sideCorner, viewModel)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Size Type - Radio buttons like original
                SizeType.entries.forEach { sizeType ->
                    SizeTypeRow(sizeType, settings, viewModel)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Symmetry Checkbox
                SymmetryCheckbox(settings, viewModel)

                // Show Image Checkbox
                ShowImageCheckbox(settings, viewModel)

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolBar(
    onNavigateBack: () -> Unit,
    viewModel: SettingsPurchaseViewModel
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                text = "Setting Purchase",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        },
        actions = {
            IconButton(
                onClick = {
                    viewModel.onSaveSettingsClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Save",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = Colors.colorPrimary
        )
    )
}

@Composable
private fun PreviewCard(
    purchaseSetting: PurchaseSetting,
    item: PurchaseModel = PurchaseModel.TEST
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Colors.colorAccent
        ),
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon photo
            if (purchaseSetting.isImage) {
                Box(
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text(
                        text = "🖼️",
                        fontSize = 24.sp
                    )
                }
            }

            // Title text
            Text(
                text = item.text,
                fontSize = 18.sp,
                style = textStyle1(),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Checkbox
            Checkbox(
                checked = item.isChecked,
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

@Composable
private fun ShapeTypeRow(
    shapeType: ShapeType,
    settings: com.veles.purchase.domain.model.setting.PurchaseSetting,
    viewModel: SettingsPurchaseViewModel
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = shapeType == settings.shapeType,
                onClick = { viewModel.onShapeTypeChanged(shapeType) }
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
            selected = shapeType == settings.shapeType,
            onClick = { viewModel.onShapeTypeChanged(shapeType) },
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = Colors.gr,
                unselectedColor = Colors.gr,
                disabledSelectedColor = Color.Black
            )
        )
        Text(
            textAlign = TextAlign.Center,
            text = shapeType.toString(),
            fontSize = 18.sp,
            style = textStyle1()
        )
    }
}

@Composable
private fun SizeTypeRow(
    sizeType: SizeType,
    settings: com.veles.purchase.domain.model.setting.PurchaseSetting,
    viewModel: SettingsPurchaseViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = sizeType == settings.sizeType,
                onClick = { viewModel.onSizeTypeChanged(sizeType) }
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = sizeType == settings.sizeType,
            onClick = { viewModel.onSizeTypeChanged(sizeType) },
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = Colors.gr,
                unselectedColor = Colors.gr,
                disabledSelectedColor = Color.Black,
                disabledUnselectedColor = Color.Black
            )
        )
        Text(
            text = sizeType.toString(),
            fontSize = 18.sp,
            style = textStyle1()
        )
    }
}

@Composable
private fun AllCornerSlider(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Slider(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChange,
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
private fun SideCornerSliders(
    sideCorner: CornerSetting,
    viewModel: SettingsPurchaseViewModel
) {
    Column {
        SliderWithValue(sideCorner.topStart) {
            viewModel.onSideCornerChanged(topStart = it)
        }
        SliderWithValue(sideCorner.topEnd) {
            viewModel.onSideCornerChanged(topEnd = it)
        }
        SliderWithValue(sideCorner.bottomStart) {
            viewModel.onSideCornerChanged(bottomStart = it)
        }
        SliderWithValue(sideCorner.bottomEnd) {
            viewModel.onSideCornerChanged(bottomEnd = it)
        }
    }
}

@Composable
private fun SliderWithValue(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Slider(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChange,
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
private fun SymmetryCheckbox(
    settings: com.veles.purchase.domain.model.setting.PurchaseSetting,
    viewModel: SettingsPurchaseViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = settings.isSymmetry,
                onClick = { viewModel.onIsSymmetryChanged(!settings.isSymmetry) }
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = settings.isSymmetry,
            onCheckedChange = { viewModel.onIsSymmetryChanged(it) },
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
private fun ShowImageCheckbox(
    settings: com.veles.purchase.domain.model.setting.PurchaseSetting,
    viewModel: SettingsPurchaseViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = settings.isImage,
                onClick = { viewModel.onIsShowImageChanged(!settings.isImage) }
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = settings.isImage,
            onCheckedChange = { viewModel.onIsShowImageChanged(it) },
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


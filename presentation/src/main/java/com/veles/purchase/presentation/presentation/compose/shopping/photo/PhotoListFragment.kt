package com.veles.purchase.presentation.presentation.compose.shopping.photo

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.skydoves.landscapist.glide.GlideImage
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.IconSquare
import com.veles.purchase.presentation.presentation.compose.Colors

class PhotoListFragment : BaseFragment() {

    private val viewModel: PhotoListViewModel by viewModels { viewModelFactory }

    private val onBack: () -> Unit = {
        findNavController().popBackStack()
    }

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
                Scaffold(
                    topBar = {
                        ToolBar()
                    },
                    bottomBar = {
                        BottomBar()
                    },
                    content = {
                        Content(it)
                    },
                    backgroundColor = Color.Black
                )
            }
        }
    }

    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues()
    ) = Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = paddingValues)
    ) {
        val skuPhotoEntity by viewModel.flowSkuPhotoEntity.collectAsState()
        val uri = try {
            Uri.parse(skuPhotoEntity?.skuPhotoUri.toString()) ?: Uri.EMPTY
        } catch (e: Exception) {
            Uri.EMPTY
        }
        when {
            uri != Uri.EMPTY -> ZoomableImage(uri)
            else -> Text(
                text = "Image not found",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }

    @Composable
    fun ZoomableImage(uri: Uri) {
        val scale = remember { mutableStateOf(1f) }
//        val translationX = remember { mutableStateOf(0.0f) }
//        val translationY = remember { mutableStateOf(0.0f) }
        Box(
            modifier = Modifier
                .clip(RectangleShape) // Clip the box content
                .fillMaxSize() // Give the size you want...
        ) {
            GlideImage(
                modifier = Modifier
                    .align(Alignment.Center) // keep the image centralized into the Box
                    .graphicsLayer(
                        // adding some zoom limits (min 50%, max 200%)
                        scaleX = maxOf(1f, minOf(3f, scale.value)),
                        scaleY = maxOf(1f, minOf(3f, scale.value))
//                        translationX = translationX.value,
//                        translationY = translationY.value,
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { _, _, zoom, _ ->
                            scale.value = when {
                                scale.value < 1f -> 1f
                                scale.value > 3f -> 3f
                                else -> scale.value * zoom
                            }
//                            translationX.value += pan.x
//                            translationY.value += pan.y
                        }
                    },
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                imageModel = uri,
                loading = {
                    CircularProgressIndicator()
                }
            )
        }
    }

    @Preview
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
                        text = "Photo",
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
                        id = R.drawable.ic_delete_black_24dp,
                        onClick = {
                            viewModel.deletePhoto(onBack)
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

    @Composable
    fun BottomBar() {
        BottomAppBar(
            backgroundColor = Colors.colorPrimaryDark.copy(alpha = 0.8.toFloat()),
            elevation = 1.dp,
            cutoutShape = CircleShape
        ) {
//            ConstraintLayout(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                val (
//                    IconPhoto,
//                    IconCalendar,
//                ) = createRefs()
//                IconSquare(
//                    id = R.drawable.ic_baseline_add_a_photo_24,
//                    onClick = {
//                    },
//                    modifier = Modifier
//                        .constrainAs(IconPhoto) {
//                            end.linkTo(parent.end)
//                            top.linkTo(parent.top)
//                            bottom.linkTo(parent.bottom)
//                        },
//                )
//                IconSquare(
//                    id = R.drawable.ic_baseline_calendar_today_24,
//                    onClick = {
//                    },
//                    modifier = Modifier
//                        .constrainAs(IconCalendar) {
//                            end.linkTo(IconPhoto.start, margin = 8.dp)
//                            top.linkTo(parent.top)
//                            bottom.linkTo(parent.bottom)
//                        },
//                )
//            }
        }
    }
}

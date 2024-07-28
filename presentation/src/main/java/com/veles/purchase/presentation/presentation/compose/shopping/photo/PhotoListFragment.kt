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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.skydoves.landscapist.glide.GlideImage
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
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
                    containerColor = Color.Black
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
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
                    text = "Photo",
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
                        viewModel.deletePhoto(onBack)

                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete_black_24dp),
                        contentDescription = "Localized description"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
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
        }
    }
}

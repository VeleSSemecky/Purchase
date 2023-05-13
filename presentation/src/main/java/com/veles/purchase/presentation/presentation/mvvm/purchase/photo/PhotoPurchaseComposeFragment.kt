package com.veles.purchase.presentation.presentation.mvvm.purchase.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import com.skydoves.landscapist.glide.GlideImage
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.CircularCenterProgressIndicator
import com.veles.purchase.presentation.compose.IconSquare
import com.veles.purchase.presentation.compose.ZoomableImage
import com.veles.purchase.presentation.presentation.compose.Colors

class PhotoPurchaseComposeFragment : BaseFragment() {

    private val viewModel: PhotoPurchaseComposeViewModel by viewModels { viewModelFactory }

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
                    },
                    content = {
                        Content(it)
                    },
                    backgroundColor = Color.Black
                )
                Progress()
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
        val purchasePhotoModelState = viewModel.flowPurchasePhotoModel.collectAsState()
        val purchasePhotoModel = purchasePhotoModelState.value
        if (purchasePhotoModel == null) {
            Text(
                text = "Image not found",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White
            )
        } else {
            ZoomableGlideImage(viewModel.apiDatabaseURL(purchasePhotoModel))
        }
    }

    @Composable
    fun ZoomableGlideImage(imageModel: Any) {
        ZoomableImage { modifier ->
            GlideImage(
                modifier = modifier,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                imageModel = imageModel,
                loading = {
                    CircularCenterProgressIndicator()
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
                            viewModel.onBackClicked()
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
                            viewModel.deletePhoto()
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
        val progress = viewModel.flowProgress.collectAsState()
        if (progress.value != com.veles.purchase.presentation.model.progress.Progress.Start) return
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.progress)
                .clickable(false) {
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Colors.gr
            )
        }
    }
}

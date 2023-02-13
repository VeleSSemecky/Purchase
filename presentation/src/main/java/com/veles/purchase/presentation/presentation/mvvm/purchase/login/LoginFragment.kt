package com.veles.purchase.presentation.presentation.mvvm.purchase.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.compose.GoogleButton
import com.veles.purchase.presentation.data.result.GoogleSignInContract
import com.veles.purchase.presentation.presentation.compose.Colors
import com.veles.purchase.presentation.presentation.compose.textStyle1

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    private val googleSignInLauncher =
        registerForActivityResult(GoogleSignInContract()) { idToken ->
            viewModel.firebaseAuthWithGoogle(idToken ?: return@registerForActivityResult)
        }

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
                ComposeContent()
            }
        }
    }

    @Preview
    @Composable
    fun ComposeContent() {
        val image = ImageBitmap.imageResource(R.drawable.ic_backq)
        val brush = remember(image) {
            ShaderBrush(
                ImageShader(
                    image,
                    TileMode.Repeated,
                    TileMode.Repeated
                )
            )
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        ) {
            val (TextTitle, IconImage, ButtonGoogle) = createRefs()
            Box(
                modifier = Modifier
                    .constrainAs(TextTitle) {
                        top.linkTo(parent.top)
                        bottom.linkTo(IconImage.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxHeight(.3f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .background(
                            color = Colors.progress,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .width(IntrinsicSize.Min)

                ) {
                    Text(
                        style = textStyle1(),
                        text = "PurchaseApp",
                        textAlign = TextAlign.Center,
                        fontSize = 42.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .constrainAs(IconImage) {
                        top.linkTo(TextTitle.bottom)
                        bottom.linkTo(ButtonGoogle.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxHeight(.5f)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.5f),
                    painter = painterResource(R.mipmap.ic_launcher_round),
                    contentDescription = "Is Image"
                )
            }
            Box(
                modifier = Modifier
                    .constrainAs(ButtonGoogle) {
                        top.linkTo(IconImage.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.2f),
                contentAlignment = Alignment.Center
            ) {
                GoogleButton(
                    Modifier.fillMaxWidth(),
                    text = "Sign Up with Google",
                    loadingText = "Creating Account...",
                    onClicked = {
                        googleSignInLauncher.launch(viewModel.googleSignInIntent())
                    }
                )
            }
        }
    }
}

package com.veles.purchase.presentation.compose.sku.scanner

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.platform.ai.GemmaModelConfig
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.mvvm.sku.scanner.GemmaSetupUiState
import com.veles.purchase.presentation.mvvm.sku.scanner.GemmaSetupViewModel
import com.veles.purchase.presentation.mvvm.sku.scanner.modelSizeMb
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GemmaSetupScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: GemmaSetupViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.refresh() }

    Scaffold(
        topBar = {
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
                title = { Text("AI Receipt Parser", fontSize = 20.sp, color = Color.White) },
                actions = {
                    if (uiState is GemmaSetupUiState.Ready) {
                        IconButton(onClick = { viewModel.deleteModel() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete model", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Colors.colorPrimary)
            )
        },
        containerColor = Colors.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ModelInfoCard()

            AnimatedContent(targetState = uiState) { state ->
                when (state) {
                    is GemmaSetupUiState.Idle -> DownloadSection(
                        onDownload = { viewModel.startDownload() }
                    )
                    is GemmaSetupUiState.Downloading -> DownloadProgressSection(percent = state.percent)
                    is GemmaSetupUiState.Ready -> ReadySection()
                    is GemmaSetupUiState.Error -> ErrorSection(
                        message = state.message,
                        onRetry = { viewModel.startDownload() }
                    )
                }
            }
        }
    }
}

@Composable
private fun ModelInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = null,
                    tint = Colors.gr,
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    "Gemma 3 1B (on-device)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Text(
                text = "Runs entirely on your device. No internet required after download.",
                color = Color.Gray,
                fontSize = 13.sp
            )
            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ModelStat("Size", "~${GemmaModelConfig.modelSizeMb} MB")
                ModelStat("Format", "INT4 quantized")
                ModelStat("Backend", "MediaPipe")
            }
        }
    }
}

@Composable
private fun ModelStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.White)
        Text(label, color = Color.Gray, fontSize = 11.sp)
    }
}

@Composable
private fun DownloadSection(onDownload: () -> Unit) {
    Button(
        onClick = onDownload,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Colors.gr)
    ) {
        Text(
            "Download Gemma 3 (~${GemmaModelConfig.modelSizeMb} MB)",
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}

@Composable
private fun DownloadProgressSection(percent: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Downloading model…", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        LinearProgressIndicator(
            progress = { percent / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = Colors.gr,
            trackColor = Color.White.copy(alpha = 0.15f)
        )
        Text("$percent%", color = Color.Gray, fontSize = 13.sp)
        Text(
            "Keep the app open during download.",
            color = Color.Gray,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ReadySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0F2A1A), RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("✅", fontSize = 40.sp)
        Text("Model ready", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
        Text(
            "Gemma 3 is installed. Receipt scanner will now use AI parsing automatically.",
            color = Color.Gray,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ErrorSection(message: String, onRetry: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2A1010))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "Download failed",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFF6B6B),
                    fontSize = 14.sp
                )
                Text(message, color = Color.Gray, fontSize = 12.sp)
            }
        }
        DownloadSection(onDownload = onRetry)
    }
}

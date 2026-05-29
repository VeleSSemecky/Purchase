package com.veles.purchase.presentation.compose.purchase.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.compose.textStyle1
import com.veles.purchase.presentation.mvvm.purchase.collection.CollectionMembersViewModel
import com.veles.purchase.presentation.mvvm.purchase.collection.MemberItem
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Screen for managing collection members.
 */
@Composable
fun CollectionMembersScreen(
    initialSelectedIds: List<String>,
    onNavigateBack: () -> Unit = {},
    onConfirm: (List<String>) -> Unit = {},
    viewModel: CollectionMembersViewModel = koinViewModel(
        parameters = { parametersOf(initialSelectedIds) }
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    CollectionMembersContent(
        members = uiState.members,
        isLoading = uiState.isLoading,
        onNavigateBack = onNavigateBack,
        onConfirm = { onConfirm(viewModel.getSelectedIds()) },
        onToggleMember = viewModel::toggleMember
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollectionMembersContent(
    members: List<MemberItem>,
    isLoading: Boolean,
    onNavigateBack: () -> Unit,
    onConfirm: () -> Unit,
    onToggleMember: (UserPurchaseModel, Boolean) -> Unit
) {
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
                title = {
                    Text(
                        text = "Select Members",
                        fontSize = 20.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = onConfirm) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Confirm",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Colors.colorPrimary
                )
            )
        },
        containerColor = Colors.surface
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (members.isEmpty() && !isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No users found", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = members,
                        key = { it.user.uid }
                    ) { item ->
                        UserMemberItem(
                            user = item.user,
                            isSelected = item.isSelected,
                            onToggle = { onToggleMember(item.user, it) }
                        )
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Colors.progress)
                        .clickable(enabled = false) {},
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Colors.gr)
                }
            }
        }
    }
}

@Composable
private fun UserMemberItem(
    user: UserPurchaseModel,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!isSelected) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Colors.colorAccent else Colors.colorAccent.copy(alpha = 0.6f)
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Colors.gr.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                val initials = user.displayName?.take(1)?.uppercase() ?: "?"
                Text(
                    text = initials,
                    color = Colors.gr,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
            ) {
                Text(
                    text = user.displayName ?: "Unknown User",
                    style = textStyle1(),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                user.email?.let { email ->
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.5f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Checkbox(
                checked = isSelected,
                onCheckedChange = onToggle,
                colors = CheckboxDefaults.colors(
                    checkedColor = Colors.gr,
                    uncheckedColor = Color.White.copy(alpha = 0.4f),
                    checkmarkColor = Color.Black
                )
            )
        }
    }
}

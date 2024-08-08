package com.veles.purchase.presentation.presentation.compose.shopping.dialog.error

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.veles.purchase.presentation.R

@Composable
fun ErrorAlertDialog(onConfirmation: () -> Unit = {}) {
    AlertDialog(
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_error),
                contentDescription = "Example Icon"
            )
        },
        title = {
            Text(text = "Whoops!")
        },
        text = {
            Text(text = "Something went wrong.")
        },
        onDismissRequest = {
            onConfirmation()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Ok")
            }
        }
    )
}

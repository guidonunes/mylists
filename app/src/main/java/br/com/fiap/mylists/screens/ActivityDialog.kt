package br.com.fiap.mylists.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.mylists.model.Activity

@Composable
fun ActivityDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    activity: Activity = Activity(),
    action: String
) {

    // Definindo o estado dos campos
    var title by remember { mutableStateOf(activity.title) }
    var subject by remember { mutableStateOf(activity.subject)}
    var deadline: String by remember { mutableStateOf(activity.deadline)}
    var done by remember { mutableStateOf(activity.done) }

    // Criando o AlertDialog
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = action)
        },
        text = {
            Column(){
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text(text = "Activity Title")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = subject,
                    onValueChange = {
                        subject = it
                    },
                    label = {
                        Text(text = "Subject")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = deadline.toString(),
                    onValueChange = {
                        deadline = it
                    },
                    label = {
                        Text(text = "Deadline")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = done,
                        onCheckedChange = {
                            done = it
                        }
                    )
                    Text(text = "Done")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newActivity = Activity(
                        title = title,
                        subject = subject,
                        deadline = deadline,
                        done = done,
                    )
                    onConfirm()
                }
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Cancel")
            }
        }
    )
}
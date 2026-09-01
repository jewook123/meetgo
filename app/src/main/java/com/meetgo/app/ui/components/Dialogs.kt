package com.meetgo.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.selectableGroup
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.meetgo.app.data.model.ReportReason
import com.meetgo.app.data.model.UserProfile

@Composable
fun MatchSuccessDialog(
    otherUser: UserProfile,
    onStartChat: () -> Unit,
    onKeepBrowsing: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onKeepBrowsing,
        title = { Text("매칭 성공! 🎉") },
        text = { Text("${otherUser.name}님과 매칭되었어요. 지금 대화를 시작해보세요.") },
        confirmButton = {
            TextButton(onClick = onStartChat) { Text("채팅 시작하기") }
        },
        dismissButton = {
            TextButton(onClick = onKeepBrowsing) { Text("계속 둘러보기") }
        },
    )
}

@Composable
fun ReportDialog(
    onDismiss: () -> Unit,
    onSubmit: (ReportReason) -> Unit,
) {
    var selected by remember { mutableStateOf(ReportReason.SPAM) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("신고하기") },
        text = {
            Column(Modifier.selectableGroup()) {
                ReportReason.entries.forEach { reason ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .selectable(
                                selected = selected == reason,
                                onClick = { selected = reason },
                                role = Role.RadioButton,
                            )
                            .padding(vertical = 4.dp),
                    ) {
                        RadioButton(selected = selected == reason, onClick = { selected = reason })
                        Text(reason.label, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onSubmit(selected) }) { Text("신고 제출") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("취소") }
        },
    )
}

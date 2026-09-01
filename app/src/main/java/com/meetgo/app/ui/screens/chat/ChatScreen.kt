package com.meetgo.app.ui.screens.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.meetgo.app.data.model.ChatMessage
import com.meetgo.app.data.model.SampleData
import com.meetgo.app.ui.components.ChatBubble
import com.meetgo.app.ui.components.ReportDialog

@Composable
fun ChatScreen(
    matchId: String,
    onBack: () -> Unit,
    onUnmatch: () -> Unit,
) {
    val match = remember(matchId) { SampleData.matches.first { it.id == matchId } }
    var messages by remember(matchId) { mutableStateOf(SampleData.chatMessages(matchId)) }
    var input by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = match.otherUser.mainPhoto,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(36.dp).clip(CircleShape),
                        )
                        Text(match.otherUser.name, modifier = Modifier.padding(start = 10.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "더보기")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("신고하기") },
                            onClick = {
                                showMenu = false
                                showReportDialog = true
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("매칭 해제") },
                            onClick = {
                                showMenu = false
                                onUnmatch()
                            },
                        )
                    }
                },
            )
        },
        bottomBar = {
            ChatInputBar(
                value = input,
                onValueChange = { input = it },
                onSend = {
                    if (input.isNotBlank()) {
                        messages = messages + ChatMessage(id = "local-${messages.size}", text = input, isMine = true)
                        input = ""
                    }
                },
                onAttachPhoto = {
                    messages = messages + ChatMessage(
                        id = "local-${messages.size}",
                        photoUrl = "https://picsum.photos/seed/chat${messages.size}/400/400",
                        isMine = true,
                    )
                },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            reverseLayout = true,
        ) {
            items(messages.reversed(), key = { it.id }) { message ->
                ChatBubble(message = message, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }

    if (showReportDialog) {
        ReportDialog(
            onDismiss = { showReportDialog = false },
            onSubmit = { showReportDialog = false },
        )
    }
}

@Composable
private fun ChatInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onAttachPhoto: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        IconButton(onClick = onAttachPhoto) {
            Icon(Icons.Filled.PhotoCamera, contentDescription = "사진 전송")
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("메시지를 입력하세요") },
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = onSend) {
            Icon(
                Icons.Filled.Send,
                contentDescription = "전송",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

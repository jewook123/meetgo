package com.meetgo.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.meetgo.app.data.model.ChatMessage

@Composable
fun ChatBubble(message: ChatMessage, modifier: Modifier = Modifier) {
    val bubbleColor = if (message.isMine) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val textColor = if (message.isMine) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val alignment = if (message.isMine) Alignment.CenterEnd else Alignment.CenterStart

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = alignment) {
        Box(
            modifier = Modifier
                .widthIn(max = 260.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isMine) 16.dp else 4.dp,
                        bottomEnd = if (message.isMine) 4.dp else 16.dp,
                    ),
                )
                .background(bubbleColor)
                .padding(if (message.photoUrl != null) 4.dp else 12.dp),
        ) {
            when {
                message.photoUrl != null -> AsyncImage(
                    model = message.photoUrl,
                    contentDescription = "전송된 사진",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .widthIn(max = 200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                )
                else -> Text(
                    message.text.orEmpty(),
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

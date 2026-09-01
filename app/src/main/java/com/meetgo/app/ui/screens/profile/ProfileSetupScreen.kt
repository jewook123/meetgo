package com.meetgo.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.meetgo.app.data.model.SampleData
import com.meetgo.app.ui.components.MeetGoPrimaryButton
import com.meetgo.app.ui.components.SelectableTagChip

private const val MAX_PHOTOS = 6

@Composable
fun ProfileSetupScreen(
    isEditMode: Boolean = false,
    onComplete: () -> Unit,
) {
    var bio by remember { mutableStateOf("") }
    val selectedInterests = remember { mutableStateListOf<String>() }
    var photoCount by remember { mutableStateOf(if (isEditMode) 1 else 0) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (isEditMode) "프로필 수정" else "프로필 설정") })
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                "사진 (최대 $MAX_PHOTOS 장)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) {
                items(photoCount) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                    )
                }
                if (photoCount < MAX_PHOTOS) {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { photoCount += 1 },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "사진 추가")
                        }
                    }
                }
            }

            Text(
                "자기소개",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                placeholder = { Text("나를 소개하는 글을 작성해보세요") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                "관심사",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
            )
            InterestTagGrid(
                options = SampleData.interestOptions,
                selected = selectedInterests,
            )

            MeetGoPrimaryButton(
                text = "완료",
                onClick = onComplete,
                enabled = photoCount > 0 && bio.isNotBlank() && selectedInterests.isNotEmpty(),
                modifier = Modifier.padding(vertical = 28.dp),
            )
        }
    }
}

@Composable
private fun InterestTagGrid(options: List<String>, selected: SnapshotStateList<String>) {
    Column {
        options.chunked(3).forEach { rowItems ->
            Row {
                rowItems.forEach { option ->
                    SelectableTagChip(
                        label = option,
                        selected = option in selected,
                        onClick = {
                            if (option in selected) selected.remove(option) else selected.add(option)
                        },
                    )
                }
            }
        }
    }
}

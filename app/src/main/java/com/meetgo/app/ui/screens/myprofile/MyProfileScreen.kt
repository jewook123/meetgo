package com.meetgo.app.ui.screens.myprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.meetgo.app.data.model.SampleData
import com.meetgo.app.ui.components.MeetGoSecondaryButton
import com.meetgo.app.ui.components.TagChip

@Composable
fun MyProfileScreen(
    onEditProfile: () -> Unit,
    onLogout: () -> Unit,
) {
    val profile = SampleData.myProfile

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("마이프로필", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            AsyncImage(
                model = profile.mainPhoto,
                contentDescription = "내 프로필 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f)
                    .clip(RoundedCornerShape(20.dp))
                    .padding(top = 12.dp),
            )
            Text(
                "${profile.name}, ${profile.age}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp),
            )
            Text(
                profile.bio,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                "관심사",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                profile.interests.forEach { interest -> TagChip(interest) }
            }

            MeetGoSecondaryButton(
                text = "프로필 수정",
                onClick = onEditProfile,
                modifier = Modifier.padding(top = 28.dp),
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

            TextButton(onClick = onLogout) {
                Text("로그아웃", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

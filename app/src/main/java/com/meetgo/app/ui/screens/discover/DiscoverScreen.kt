package com.meetgo.app.ui.screens.discover

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.meetgo.app.data.model.SampleData
import com.meetgo.app.data.model.UserProfile
import com.meetgo.app.ui.components.MatchSuccessDialog
import com.meetgo.app.ui.components.ProfileFeedCard

@Composable
fun DiscoverScreen(
    onProfileClick: (String) -> Unit,
    onMatched: (otherUserId: String) -> Unit,
) {
    var profiles by remember {
        mutableStateOf(SampleData.discoverProfiles.sortedBy { it.distanceKm })
    }
    var matchedProfile by remember { mutableStateOf<UserProfile?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("탐색", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                },
            )
        },
    ) { padding ->
        if (profiles.isEmpty()) {
            EmptyDiscoverState(padding)
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize().padding(padding),
            ) {
                items(profiles, key = { it.id }) { profile ->
                    ProfileFeedCard(
                        profile = profile,
                        onClick = { onProfileClick(profile.id) },
                        onLike = {
                            profiles = profiles.filterNot { it.id == profile.id }
                            if (profile.id in SampleData.profileIdsWhoLikeMe) {
                                matchedProfile = profile
                            }
                        },
                        onSkip = { profiles = profiles.filterNot { it.id == profile.id } },
                        modifier = Modifier.padding(bottom = 16.dp),
                    )
                }
            }
        }
    }

    matchedProfile?.let { profile ->
        MatchSuccessDialog(
            otherUser = profile,
            onStartChat = {
                matchedProfile = null
                onMatched(profile.id)
            },
            onKeepBrowsing = { matchedProfile = null },
        )
    }
}

@Composable
private fun EmptyDiscoverState(padding: PaddingValues) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        Text("주변에 더 이상 표시할 프로필이 없어요.", style = MaterialTheme.typography.bodyLarge)
    }
}

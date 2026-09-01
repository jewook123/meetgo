package com.meetgo.app.ui.screens.matches

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.meetgo.app.data.model.SampleData
import com.meetgo.app.ui.components.MatchListItem

@Composable
fun MatchesScreen(onMatchClick: (matchId: String) -> Unit) {
    val matches = SampleData.matches

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("매칭", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                },
            )
        },
    ) { padding ->
        if (matches.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text("아직 매칭된 상대가 없어요.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                items(matches, key = { it.id }) { match ->
                    MatchListItem(match = match, onClick = { onMatchClick(match.id) })
                    HorizontalDivider(modifier = Modifier.padding(start = 84.dp))
                }
            }
        }
    }
}

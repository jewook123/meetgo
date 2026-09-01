package com.meetgo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.meetgo.app.navigation.MeetGoNavHost
import com.meetgo.app.ui.theme.MeetGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeetGoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MeetGoNavHost()
                }
            }
        }
    }
}

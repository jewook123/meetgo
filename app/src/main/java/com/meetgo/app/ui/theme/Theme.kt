package com.meetgo.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Coral40,
    onPrimary = Neutral99,
    secondary = Plum40,
    onSecondary = Neutral99,
    tertiary = Peach40,
    background = Neutral99,
    onBackground = Neutral10,
    surface = Neutral99,
    onSurface = Neutral10,
    surfaceVariant = Peach90,
    error = ErrorRed,
)

private val DarkColors = darkColorScheme(
    primary = Coral80,
    onPrimary = Coral20,
    secondary = Plum80,
    onSecondary = Plum20,
    tertiary = Peach40,
    background = Neutral10,
    onBackground = Neutral90,
    surface = Neutral20,
    onSurface = Neutral90,
    error = ErrorRed,
)

@Composable
fun MeetGoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

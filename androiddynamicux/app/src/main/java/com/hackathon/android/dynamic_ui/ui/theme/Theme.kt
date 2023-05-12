package com.hackathon.android.dynamic_ui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun AndroidDynamicUxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        lightColors(
            primary = PricelineBlue,
            primaryVariant = DarkBlue,
            secondary = Green,
            secondaryVariant = DarkGreen,
            onSecondary = White,
            background = BaseGray,
            surface = White,
            onError = White,
            onSurface = DarkerBlue,
        )
    } else {
        lightColors(
            primary = PricelineBlue,
            primaryVariant = DarkBlue,
            secondary = Green,
            secondaryVariant = DarkGreen,
            onSecondary = White,
            background = BaseGray,
            surface = White,
            onError = White,
            onSurface = DarkerBlue,
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
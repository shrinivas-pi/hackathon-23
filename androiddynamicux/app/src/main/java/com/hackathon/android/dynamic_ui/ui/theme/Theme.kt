package com.hackathon.android.dynamic_ui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AndroidDynamicUxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = PricelineBlue,
            primaryVariant = DarkBlue,
            secondary = Green
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
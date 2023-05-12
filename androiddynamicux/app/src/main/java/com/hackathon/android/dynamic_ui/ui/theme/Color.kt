package com.hackathon.android.dynamic_ui.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.hackathon.android.dynamic_ui.R

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val PricelineBlue
    @Composable get() = colorResource(id = R.color.priceline_blue)
val DarkBlue
    @Composable get() = colorResource(id = R.color.dark_blue)
val DarkerBlue
    @Composable get() = colorResource(id = R.color.darker_blue)
val Green
    @Composable get() = colorResource(id = R.color.green)
val DarkGreen
    @Composable get() = colorResource(id = R.color.dark_green)
val Gray
    @Composable get() = colorResource(id = R.color.gray)
val LightGray
    @Composable get() = colorResource(id = R.color.light_gray)
val BaseGray
    @Composable get() = colorResource(id = R.color.base_gray)
val White
    @Composable get() = colorResource(id = R.color.white)
val White38 // 38% Opacity
    @Composable get() = colorResource(id = R.color.white_38)
val White60 // 60% Opacity
    @Composable get() = colorResource(id = R.color.white_38)
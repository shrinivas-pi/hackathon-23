package com.hackathon.android.dynamic_ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DEFAULT_TEXT_SIZE = 16.sp
private val DEFAULT_RADIUS = 8.dp

fun UiAttributes?.toProperties() = ComposeProperties(
    foregroundColor = Color(android.graphics.Color.parseColor(this?.foregroundColor)),
    backgroundColor = Color(android.graphics.Color.parseColor(this?.backgroundColor)),
    textStyle = TextStyle(
        fontSize = this?.font?.size?.sp ?: DEFAULT_TEXT_SIZE
    ),
    size = this?.size,
    radius = this?.radius?.dp
)

data class ComposeProperties(
    val foregroundColor: Color,
    val backgroundColor: Color,
    val textStyle: TextStyle,
    val size: Size?,
    val radius: Dp?
)

val String.color
    get() = Color(android.graphics.Color.parseColor(this))
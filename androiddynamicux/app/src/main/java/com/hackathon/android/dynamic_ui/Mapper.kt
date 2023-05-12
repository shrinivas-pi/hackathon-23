package com.hackathon.android.dynamic_ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DEFAULT_TEXT_SIZE = 16.sp
private val DEFAULT_RADIUS = 8.dp

fun UiAttributes.toProperties() = ComposeProperties(
    foregroundColor = Color(android.graphics.Color.parseColor(this.foregroundColor)),
    backgroundColor = this.backgroundColor?.let {
        Color(android.graphics.Color.parseColor(this.backgroundColor))
    },
    textStyle = TextStyle(
        fontSize = this.font?.size?.sp ?: DEFAULT_TEXT_SIZE,
        fontWeight = this.font?.type.toFontType(),
        fontFamily = RobotoFontFamily
    ),
    size = this.size,
    radius = this.radius?.dp,
    isTapabble = this.isTapabble ?: false
)

private fun String?.toFontType(): FontWeight = when(this) {
    "bold" -> FontWeight.Bold
    "medium" -> FontWeight.Medium
    else -> FontWeight.Normal
}

data class ComposeProperties(
    val foregroundColor: Color? = null,
    val backgroundColor: Color? = null,
    val textStyle: TextStyle,
    val size: Size?,
    val radius: Dp?,
    val isTapabble: Boolean = false
)

val String.color
    get() = Color(android.graphics.Color.parseColor(this))

private val RobotoFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.roboto_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.roboto_bold,
            weight = FontWeight.Bold
        ),
        Font(
            resId = R.font.roboto_medium,
            weight = FontWeight.Medium
        )
    )
)
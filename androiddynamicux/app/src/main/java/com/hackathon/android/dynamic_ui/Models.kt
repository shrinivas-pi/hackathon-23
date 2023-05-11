package com.hackathon.android.dynamic_ui

data class UiElement(
    val id: String,
    val type: String,
    val link: String,
    val text: String,
    val title: String,
    val properties: UiAttributes,
    val children: List<UiElement>,
)

data class UiAttributes(
    var scrollEnabled: Boolean,
    val isTapabble: Boolean,
    val isNetworkLoadable: Boolean,
    val font: Font,
    val foregroundColor: String,
    val backgroundColor: String,
    val padding: Padding,
    val radius: Int,
    val size: Size,
)

data class Size(
    val width: Double,
    val height: Double
)

data class Font(
    val size: Int,
    val type: String
)

data class Padding(
    val left: Int = 0,
    val right: Int = 0,
    val top: Int = 0,
    val bottom: Int = 0
)
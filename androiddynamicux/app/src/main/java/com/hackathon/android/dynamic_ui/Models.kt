package com.hackathon.android.dynamic_ui

data class UiElement(
    val id: String?,
    val type: String?,
    val link: String?,
    val text: String?,
    val title: String?,
    val properties: UiAttributes?,
    val children: List<UiElement>?,
    val placeHolder: String?,
    val rightIcon: UiElement?,
    val leftIcon: UiElement?,
    val rightSecondIcon: UiElement?
)

data class UiAttributes(
    var scrollEnabled: Boolean?,
    val isTapabble: Boolean?,
    val isNetworkLoadable: Boolean?,
    val font: Font?,
    val foregroundColor: String?,
    val backgroundColor: String?,
    val padding: Padding?,
    val radius: Int?,
    val size: Size?,
)

data class Size(
    val width: Double?,
    val height: Double?
)

data class Font(
    val size: Int?,
    val type: String?
)

data class Padding(
    val left: Int?,
    val right: Int?,
    val top: Int?,
    val bottom: Int?
)
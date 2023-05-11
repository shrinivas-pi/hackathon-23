package com.hackathon.android.dynamic_ui

data class UiElement(
    val element: String,
    val endpoint: String,
    val attributes: UiAttributes,
    val children: List<UiElement>,
)

data class UiAttributes(
    var text: String,
    val type: String,
    val placeholder: String,
    val regex: String?,
    val errorMessage: String?,
    val modifierOptions: ModifierOptions,
    val jsonKey: String?,
)

data class ModifierOptions(
    val fillMaxWidth: Boolean = false,
    val paddingStart: Int,
    val paddingEnd: Int,
    val paddingTop: Int,
    val paddingBottom: Int,
)
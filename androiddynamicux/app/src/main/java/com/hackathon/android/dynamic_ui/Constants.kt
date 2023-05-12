package com.hackathon.android.dynamic_ui

object Constants {

    interface TextFieldType {
        companion object {
            const val EMAIL = "email"
            const val PASSWORD = "password"
            const val TEXT = "text"
            const val PHONE = "phone"
        }
    }

    interface Element {
        companion object {
            const val COLUMN = "vContainer"
            const val ROW = "hContainer"
            const val LABEL = "text"
            const val IMAGE = "image"
//            const val TEXT = "text"
//            const val FULL_WIDTH_TEXT = "full-width-text"
            const val BUTTON = "button"
            const val SELECTION_PICKER = "selectionPicker"
//            const val LIST_COLUMN = "list-column"
//            const val LIST_ROW = "list-row"
        }
    }
}
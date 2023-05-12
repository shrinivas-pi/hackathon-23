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

    sealed interface Element {
        val id : String

        object COLUMN : Element {
            override val id: String
                get() = "vContainer"
        }

        object ROW : Element {
            override val id: String
                get() = "hContainer"
        }

        object LABEL : Element {
            override val id: String
                get() = "text"
        }

        object IMAGE : Element {
            override val id: String
                get() = "image"
        }

        object BUTTON : Element {
            override val id: String
                get() = "button"
        }

        object SELECTION_PICKER : Element {
            override val id: String
                get() = "selectionPicker"
        }
    }
}
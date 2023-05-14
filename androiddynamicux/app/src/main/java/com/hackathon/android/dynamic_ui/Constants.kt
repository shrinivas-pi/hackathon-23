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
        val id: String

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

        object BOX : Element {
            override val id: String
                get() = "box"
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

    sealed interface RenderType {
        val id: String

        object CARD : RenderType {
            override val id: String
                get() = "card"
        }

        object IMAGE : RenderType {
            override val id: String
                get() = "image"
        }

        object NONE : RenderType {
            override val id: String
                get() = "none"
        }
    }

    sealed interface Actions{
        val id : String
        object DatePicker : Actions {
            override val id: String
                get() = "datePicker"

        }
        object LocationPicker : Actions {
            override val id: String
                get() = "locationPicker"

        }
        object Banner : Actions {
            override val id: String
                get() = "vipBanner"

        }
        object Login : Actions {
            override val id: String
                get() = "loginButton"

        }

        object PlusIcon : Actions {
            override val id: String
                get() = "plusIcon"

        }

        object MinusIcon : Actions {
            override val id: String
                get() = "minusIcon"

        }
        object Banners : Actions {
            override val id: String
                get() = "marketing_banner"

        }
    }
}
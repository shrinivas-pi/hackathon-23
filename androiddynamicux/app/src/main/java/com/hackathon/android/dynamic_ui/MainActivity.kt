package com.hackathon.android.dynamic_ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hackathon.android.dynamic_ui.ui.theme.AndroidDynamicUxTheme
import java.io.IOException

private const val DEFAULT_IMAGE_HEIGHT = 160
private const val DEFAULT_BUTTON_HEIGHT = 50
private const val DEFAULT_TEXT_HEIGHT = 40
private const val DEFAULT_PADDING = 0

class MainActivity : ComponentActivity() {

    private var json: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        json = getJsonDataFromAsset(context = applicationContext, fileName = "layout.json")
        setContent {
            AndroidDynamicUxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val uiElement: UiElement =
                        Gson().fromJson(json, object : TypeToken<UiElement>() {}.type)
                    BuildView(element = uiElement)
                }
            }
        }
    }
}


//LIST, IMAGE_VIEW,
@Composable
fun BuildView(element: UiElement) {
    val renderType = element.properties?.renderType?.getRenderType()
    when (element.type) {
        Constants.Element.COLUMN.id -> BuildColumnView(element = element, renderType)
        Constants.Element.ROW.id -> BuildRowView(element = element, renderType)
        Constants.Element.LABEL.id -> BuildLabelView(element = element, renderType)
        Constants.Element.IMAGE.id -> BuildImageView(element = element, renderType)
        Constants.Element.SELECTION_PICKER.id -> BuildContainerView(element = element, renderType)
//    Constants.Element.TEXT -> BuildTextView(element = element)
//    Constants.Element.FULL_WIDTH_TEXT -> BuildFullWidthTextView(element = element)
        Constants.Element.BUTTON.id -> BuildButtonView(element = element, renderType)
//    Constants.Element.LIST_COLUMN -> data?.let { BuildListColumnView(element = element, data = it) }
//    Constants.Element.LIST_ROW -> data?.let { BuildListRowView(element = element, data = it) }
        else -> {}//TODO("add other element cases here")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BuildCardView(
    element: UiElement,
    modifier: Modifier = Modifier,
    buildChildView: @Composable () -> Unit
) {
    val mContext = LocalContext.current
    val properties = element.properties.toProperties()
    Card(
        modifier = Modifier
            .padding(
                start = element.properties?.padding?.left?.dp ?: DEFAULT_PADDING.dp,
                top = element.properties?.padding?.top?.dp ?: DEFAULT_PADDING.dp,
                end = element.properties?.padding?.right?.dp ?: DEFAULT_PADDING.dp,
                bottom = element.properties?.padding?.bottom?.dp ?: DEFAULT_PADDING.dp
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = properties.shape.topStart,
            topEnd = properties.shape.topEnd,
            bottomStart = properties.shape.bottomStart,
            bottomEnd = properties.shape.bottomEnd
        ),
        elevation = 5.dp,
        onClick = {
            performClick(mContext, element.properties?.isTapabble, element.id)
        }
    ) {
        buildChildView()
    }
}

@Composable
fun BuildRenderTypeView(
    element: UiElement,
    renderType: Constants.RenderType?,
    buildChildView: @Composable () -> Unit
) {
    when (renderType) {
        is Constants.RenderType.CARD -> BuildCardView(
            element,
            buildChildView = buildChildView
        )
        is Constants.RenderType.IMAGE -> buildChildView()
        is Constants.RenderType.NONE -> buildChildView()
        else -> buildChildView()
    }

}

private fun String.getRenderType(): Constants.RenderType =
    when (this) {
        Constants.RenderType.CARD.id -> Constants.RenderType.CARD
        Constants.RenderType.IMAGE.id -> Constants.RenderType.IMAGE
        else -> Constants.RenderType.NONE
    }


@Composable
fun BuildButtonView(
    element: UiElement,
    renderType: Constants.RenderType?,
    modifier: Modifier = Modifier
) {
    val mContext = LocalContext.current
    Button(
        modifier = getModifier(modifier, element, Constants.Element.BUTTON),
        colors = buttonColors(
            containerColor = element.properties?.backgroundColor?.color
                ?: MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(element.properties?.radius?.dp ?: 0.dp),
        onClick = {
            performClick(mContext, element.properties?.isTapabble, element.id)
        }
    ) {
        element.title?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                fontFamily = element.properties?.toProperties()?.textStyle?.fontFamily
            )
        }
    }
}

@Composable
fun buttonColors(
    containerColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(containerColor),
    disabledContainerColor: Color = MaterialTheme.colors.secondary.copy(alpha = 0.12f)
        .compositeOver(MaterialTheme.colors.surface),
    disabledContentColor: Color = MaterialTheme.colors.secondary
        .copy(alpha = ContentAlpha.disabled)
): ButtonColors = ButtonDefaults.buttonColors(
    backgroundColor = containerColor,
    contentColor = contentColor,
    disabledBackgroundColor = disabledContainerColor,
    disabledContentColor = disabledContentColor
)

/*//@Composable
//fun BuildTextView(element: UiElement) {
//    var text by rememberSaveable {
//        mutableStateOf("")
//    }
//
//    val keyboardOptions: KeyboardOptions = when {
//        element.attributes.type.equals("password", true) -> {
//            KeyboardOptions(keyboardType = KeyboardType.Password)
//        }
//
//        element.attributes.type.equals("email", true) -> {
//            KeyboardOptions(keyboardType = KeyboardType.Email)
//        }
//
//        else -> {
//            KeyboardOptions(keyboardType = KeyboardType.Text)
//        }
//    }
//
//    val modifier = Modifier
//        .padding(
//            start = element.attributes.modifierOptions.paddingStart.dp,
//            top = element.attributes.modifierOptions.paddingTop.dp,
//            end = element.attributes.modifierOptions.paddingEnd.dp,
//            bottom = element.attributes.modifierOptions.paddingBottom.dp
//        )
//    OutlinedTextField(
//        modifier = modifier,
//        value = text,
//        onValueChange = { text = it },
//        keyboardOptions = keyboardOptions,
//        label = { Text(text = element.attributes.placeholder) }
//    )
//}

//@Composable
//fun BuildFullWidthTextView(element: UiElement) {
//    var text by rememberSaveable { mutableStateOf("") }
//    var showError by rememberSaveable { mutableStateOf(false) }
//
//    var passwordVisibility by remember {
//        mutableStateOf(
//            !element.attributes.type.equals("password", true)
//        )
//    }
//    var image: ImageVector? = null
//
//    val keyboardOptions: KeyboardOptions = when {
//        element.attributes.type.equals(Constants.TextFieldType.PASSWORD, true) -> {
//            KeyboardOptions(keyboardType = KeyboardType.Password)
//        }
//
//        element.attributes.type.equals(Constants.TextFieldType.EMAIL, true) -> {
//            KeyboardOptions(keyboardType = KeyboardType.Email)
//        }
//
//        else -> {
//            KeyboardOptions(keyboardType = KeyboardType.Text)
//        }
//    }
//
//    if (element.attributes.type.equals("password", true)) {
//        image = if (passwordVisibility)
//            Icons.Filled.Visibility
//        else Icons.Filled.VisibilityOff
//    }
//
//    val modifier = Modifier
//        .fillMaxWidth()
//        .padding(
//            start = element.attributes.modifierOptions.paddingStart.dp,
//            top = element.attributes.modifierOptions.paddingTop.dp,
//            end = element.attributes.modifierOptions.paddingEnd.dp,
//            bottom = element.attributes.modifierOptions.paddingBottom.dp
//        )
//    OutlinedTextField(
//        modifier = modifier,
//        value = text,
//        keyboardOptions = keyboardOptions,
//        onValueChange = {
//            text = it
//            showError = element.attributes.regex?.let { it1 -> regexValidator(it1, text) } == false
//        },
//        isError = showError,
//        label = { Text(text = element.attributes.placeholder) },
//        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
//        trailingIcon = {
//            IconButton(onClick = {
//                passwordVisibility = !passwordVisibility
//            }) {
//            }
//            image?.let { Icon(imageVector = it, "") }
//        },
//    )
//    if (showError) {
//        element.attributes.errorMessage?.let {
//            Text(
//                text = it,
//                color = MaterialTheme.colors.error,
//                style = MaterialTheme.typography.caption,
//                modifier = Modifier
//                    .padding(start = 8.dp)
//                    .fillMaxWidth()
//            )
//        }
//    }
//}*/

@Composable
fun BuildLabelView(element: UiElement, renderType: Constants.RenderType? = null, modifier: Modifier = Modifier) {
    val properties = element.properties?.toProperties()
    element.text?.let {
        properties?.textStyle?.let { textStyle ->
            Text(
                text = it,
                style = textStyle,
                color = properties.foregroundColor ?: Color.Cyan,
                modifier = getModifier(modifier, element, Constants.Element.LABEL)
            )
        }
    }
}

@Composable
fun BuildImageView(
    element: UiElement,
    renderType: Constants.RenderType? = null,
    modifier: Modifier = Modifier
) {
    val mContext = LocalContext.current
    element.link.takeIf { it?.isNotBlank() == true }?.let {
        val image =
            if (element.properties?.isNetworkLoadable == true) {
                rememberAsyncImagePainter(element.link)
            } else {
                element.link?.let {
                    painterResource(
                        getAssetResourceId(
                            mContext,
                            it
                        )
                    )
                }
            }
        image?.let {
            Image(
                painter = it,
                contentScale = getImageContentScale(element.properties),
                contentDescription = null,
                modifier = getModifier(modifier, element, Constants.Element.IMAGE).clickable {
                    performClick(mContext, element.properties?.isTapabble, element.id)
                },
            )
        }
    }
}

fun getImageContentScale(properties: UiAttributes?): ContentScale =
    if (properties?.fillView == true) {
        ContentScale.FillBounds
    } else if (properties?.fitInside == true) {
        ContentScale.Inside
    } else {
        ContentScale.None
    }


fun getModifier(
    modifier: Modifier,
    element: UiElement,
    type: Constants.Element,
): Modifier = (element.properties?.size?.width?.let {
    modifier.size(
        height = getHeightByType(type, element.properties.size.height?.dp),
        width = it.dp
    )
} ?: modifier
    .fillMaxWidth(
    )
    .height(getHeightByType(type, element.properties?.size?.height?.dp)))
    .padding(
        start = element.properties?.padding?.left?.dp ?: DEFAULT_PADDING.dp,
        top = element.properties?.padding?.top?.dp ?: DEFAULT_PADDING.dp,
        end = element.properties?.padding?.right?.dp ?: DEFAULT_PADDING.dp,
        bottom = element.properties?.padding?.bottom?.dp ?: DEFAULT_PADDING.dp
    )
    .clip(shape = RoundedCornerShape(element.properties?.radius?.dp ?: 0.dp))


fun getHeightByType(type: Constants.Element, propertiesHeight: Dp?): Dp =
    propertiesHeight
        ?: when (type) {
            is Constants.Element.COLUMN -> DEFAULT_BUTTON_HEIGHT.dp
            is Constants.Element.IMAGE -> DEFAULT_IMAGE_HEIGHT.dp
            is Constants.Element.BUTTON -> DEFAULT_BUTTON_HEIGHT.dp
            is Constants.Element.ROW -> DEFAULT_BUTTON_HEIGHT.dp
            is Constants.Element.LABEL -> DEFAULT_TEXT_HEIGHT.dp
            is Constants.Element.SELECTION_PICKER -> DEFAULT_BUTTON_HEIGHT.dp
        }


fun getAssetResourceId(mContext: Context, fileName: String): Int =
    mContext.resources.getIdentifier(
        fileName,
        "drawable",
        mContext.packageName
    )


fun performClick(mContext: Context, isTappable: Boolean?, id: String?) {
    isTappable.takeIf { it == true && id != null }?.let {
        when (id) {
            Constants.Actions.DatePicker.id -> {}
        }
    }
}

@Composable
fun BuildColumnView(element: UiElement, renderType: Constants.RenderType? = null) =
    BuildRenderTypeView(element = element, renderType, buildChildView = {
        Column(
            modifier = Modifier.padding(
                start = element.properties?.padding?.left?.dp ?: DEFAULT_PADDING.dp,
                top = element.properties?.padding?.top?.dp ?: DEFAULT_PADDING.dp,
                end = element.properties?.padding?.right?.dp ?: DEFAULT_PADDING.dp,
                bottom = element.properties?.padding?.bottom?.dp ?: DEFAULT_PADDING.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            element.children?.forEach { BuildView(it) }
        }
    })


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BuildContainerView(
    element: UiElement,
    renderType: Constants.RenderType? = null,
    modifier: Modifier = Modifier
) {
    BuildRenderTypeView(element = element, renderType, buildChildView = {
        Row(
            modifier = getModifier(modifier, element, Constants.Element.BUTTON),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            val properties = element.properties.toProperties()
            Container(
                element = element,
                placeHolderText = element.placeHolder,
                leftIcon = element.leftIcon,
                rightIcon = element.rightIcon,
                rightSecondIcon = element.rightSecondIcon,
                properties = properties
            )
        }
    })
}

@Composable
fun BuildRowView(element: UiElement, renderType: Constants.RenderType? = null) {
    BuildRenderTypeView(element = element, renderType, buildChildView = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            element.children?.forEach { BuildView(it) }
        }
    })
}

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

/*//fun getScreenHeightWidth(context: Context): Pair<Float, Float> {
//    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
//    val dpHeight = displayMetrics.heightPixels / displayMetrics.density
//    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
//    return Pair(dpHeight, dpWidth)
//}

//@Composable
//fun BuildListColumnView(element: UiElement, data: List<JsonObject?>) {
//    ShowColumnList(element.children, data)
//}

//@Composable
//fun ShowColumnList(itemView: List<UiElement>, data: List<JsonObject?>) {
//    LazyColumn(modifier = Modifier.fillMaxWidth()) {
//        itemsIndexed(data) { index, _ ->
//            ListRow(itemView = itemView, data[index])
//            Divider()
//        }
//    }
//}

//@Composable
//fun ShowRowList(itemView: List<UiElement>, data: List<JsonObject?>) {
//    LazyRow(modifier = Modifier.fillMaxWidth()) {
//        itemsIndexed(data) { index, _ ->
//            ListRow(itemView = itemView, data = data[index])
//            Divider()
//        }
//    }
//}

//@Composable
//fun BuildListRowView(element: UiElement, data: List<JsonObject?>) {
//    ShowRowList(element.children, data)
//}

//@Composable
//fun ListRow(itemView: List<UiElement>, data: JsonObject?) {
//    itemView.forEachIndexed { index, uiElement ->
//        uiElement.attributes.text = data?.get(uiElement.attributes.jsonKey)?.asString ?: ""
//        BuildView(uiElement)
//    }
//}*/

        @Composable
        fun Greeting(name: String) {
            Text(text = "Hello $name!")
        }

        @Preview(showBackground = true)
        @Composable
        fun DefaultPreview() {
            AndroidDynamicUxTheme {
                Greeting("Android")
            }
        }
package com.hackathon.android.dynamic_ui

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.hackathon.android.dynamic_ui.ui.theme.AndroiddynamicuxTheme
import java.io.IOException
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {

    private var json: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        json = getJsonDataFromAsset(context = applicationContext, fileName = "layout.json")
        setContent {
            AndroiddynamicuxTheme {
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
fun BuildView(element: UiElement, data: List<JsonObject?>? = null) = when (element.type) {
    Constants.Element.COLUMN -> BuildColumnView(element = element)
    Constants.Element.ROW -> BuildRowView(element = element)
    Constants.Element.LABEL -> BuildLabelView(element = element)
//    Constants.Element.IMAGE -> BuildImageView(element = element)
//    Constants.Element.TEXT -> BuildTextView(element = element)
//    Constants.Element.FULL_WIDTH_TEXT -> BuildFullWidthTextView(element = element)
    Constants.Element.BUTTON -> BuildButtonView(element = element)
//    Constants.Element.LIST_COLUMN -> data?.let { BuildListColumnView(element = element, data = it) }
//    Constants.Element.LIST_ROW -> data?.let { BuildListRowView(element = element, data = it) }
    else -> {}//TODO("add other element cases here")
}

@Composable
fun BuildButtonView(element: UiElement, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { /*TODO*/ }
    ) {
        Text(text = element.title)
    }
}

//@Composable
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
//}

@Composable
fun BuildLabelView(element: UiElement) {
    Text(text = element.text)
}

@Composable
fun BuildImageView(element: UiElement) {
    Image(
        painter = rememberAsyncImagePainter(element.link),
        contentDescription = null,
//        modifier = Modifier.size(
//            height = element.properties.size.height.dp,
//            width = element.properties.size.width.dp
//        )
    )
}

@Composable
fun BuildColumnView(element: UiElement) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
//        modifier = Modifier.padding(
//            start = element.properties.padding.left.dp,
//            top = element.properties.padding.top.dp,
//            end = element.properties.padding.right.dp,
//            bottom = element.properties.padding.bottom.dp
//        )
    ) {
        element.children.forEach { BuildView(it) }
    }
}

@Composable
fun BuildRowView(element: UiElement) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        element.children.forEach { BuildView(it) }
    }
}

//fun regexValidator(regex: String, input: String) =
//    Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
//        .matcher(input)
//        .find()

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

//fun getScreenHeightWidth(context: Context): Pair<Float, Float> {
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
//}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroiddynamicuxTheme {
        Greeting("Android")
    }
}
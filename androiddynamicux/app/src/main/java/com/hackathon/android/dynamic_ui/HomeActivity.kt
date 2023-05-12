package com.hackathon.android.dynamic_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hackathon.android.dynamic_ui.ui.theme.AndroidDynamicUxTheme

class HomeActivity : ComponentActivity() {

    private var json: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        json = getJsonDataFromAsset(context = applicationContext, fileName = "home.json")
        setContent {
            AndroidDynamicUxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiElement: UiElement =
                        Gson().fromJson(json, object : TypeToken<UiElement>() {}.type)
                    BuildView(element = uiElement)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidDynamicUxTheme {
        val json = getJsonDataFromAsset(context = LocalContext.current, fileName = "home.json")
        val uiElement: UiElement =
            Gson().fromJson(json, object : TypeToken<UiElement>() {}.type)
        BuildView(element = uiElement)
    }
}
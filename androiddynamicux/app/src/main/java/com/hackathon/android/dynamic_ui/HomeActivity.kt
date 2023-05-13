package com.hackathon.android.dynamic_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hackathon.android.dynamic_ui.ui.theme.AndroidDynamicUxTheme

class HomeActivity : ComponentActivity() {

    private var json: String? = null
    lateinit var selectedDateText: MutableState<String>

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
                    val navController: NavHostController = rememberNavController()
                    selectedDateText = remember { mutableStateOf("") }
                    val uiElement: UiElement =
                        Gson().fromJson(json, object : TypeToken<UiElement>() {}.type)
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { BuildView(
                            element = uiElement,
                            navController = navController,
                            selectedDateText = selectedDateText
                        )}
                        composable("datePicker") { RangeDatePicker(onClick = { selectedYear, selectedMonth, selectedDayOfMonth ->
                            selectedDateText.value = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                        }) }
                    }
                    BuildView(element = uiElement, navController, selectedDateText)
                }
            }
        }
    }
}
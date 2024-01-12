package dev.ashish.talkies

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.ashish.talkies.navigation.NavGraph
import dev.ashish.talkies.ui.theme.TalkiesTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalkiesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navHostController = navController, startDestination = "")
                    val sharedPreferences : SharedPreferences = this.getSharedPreferences("MySharedPref",
                        Context.MODE_PRIVATE)
                    val currentPage = sharedPreferences.getInt("currentPage",0)
                    Log.d("MainActivity", currentPage.toString())
                }
            }
        }
    }
}



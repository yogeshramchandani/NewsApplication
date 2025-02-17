package com.example.realtimenewsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.realtimenewsapplication.ui.theme.RealTimeNewsApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RealTimeNewsApplicationTheme {
                val navController = rememberNavController()
                Surface {
                    Scaffold(

                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        },
                        content = { padding ->
                            NavHostContainer(
                                navController = navController,
                                padding = padding
                            )
                        },
                        topBar = {
                            val currentBackStackEntry = navController.currentBackStackEntryAsState()
                            val showBackButton =
                                currentBackStackEntry.value?.destination?.route != "home" // Replace with your root screen's route

                            TopAppBar(
                                navigationIcon = {
                                    if (showBackButton) {
                                        IconButton(onClick = {
                                            navController.popBackStack() // Handle back navigation
                                        }) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Back"
                                            )
                                        }
                                    }
                                },
                                title = {Text("News App")}
                            )

                        }
                    )
                }
            }
        }
    }
}

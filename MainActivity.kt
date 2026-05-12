package com.example.nammamistri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.example.nammamistri.ui.*
import com.example.nammamistri.ui.theme.NammaMistriTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NammaMistriTheme {
                // Persistent state for login status
                var isLoggedIn by rememberSaveable { mutableStateOf(false) }

                if (!isLoggedIn) {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true })
                } else {
                    // Default to tab 1 ("Team")
                    var selectedTab by rememberSaveable { mutableIntStateOf(1) }

                    val tabs = listOf("Calculator", "Team", "Photos", "Info")

                    Scaffold(
                        topBar = {
                            // Standard TopBar for all tabs except "Team" (which has its own custom header)
                            if (selectedTab != 1) {
                                CenterAlignedTopAppBar(
                                    title = { Text(stringResource(R.string.app_name)) },
                                    actions = {
                                        IconButton(onClick = { isLoggedIn = false }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                                contentDescription = "Logout"
                                            )
                                        }
                                    }
                                )
                            }
                        },
                        bottomBar = {
                            NavigationBar {
                                tabs.forEachIndexed { index, title ->
                                    NavigationBarItem(
                                        selected = selectedTab == index,
                                        onClick = { selectedTab = index },
                                        label = { Text(title) },
                                        icon = {
                                            when (index) {
                                                0 -> Icon(Icons.Default.Edit, null)
                                                1 -> Icon(Icons.Default.Build, null)
                                                2 -> Icon(Icons.Default.PhotoCamera, null)
                                                3 -> Icon(Icons.Default.Info, null)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        when (selectedTab) {
                            0 -> CalculatorScreen(innerPadding)
                            1 -> LaborScreen(innerPadding)
                            2 -> PhotosScreen(innerPadding)
                            3 -> RatesScreen(innerPadding)
                        }
                    }
                }
            }
        }
    }
}

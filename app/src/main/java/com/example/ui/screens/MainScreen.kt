package com.example.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.viewmodel.DashboardViewModel

@Composable
fun MainScreen(
    dashboardViewModel: DashboardViewModel,
    onNavigateToEditor: (Int) -> Unit,
    onNavigateToAiScript: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") },
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.TagFaces, contentDescription = null) },
                    label = { Text("Meme Hub") },
                    selected = currentRoute == "meme_hub",
                    onClick = {
                        navController.navigate("meme_hub") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryMusic, contentDescription = null) },
                    label = { Text("Assets") },
                    selected = currentRoute == "assets",
                    onClick = {
                        navController.navigate("assets") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                val projects by dashboardViewModel.uiState.collectAsState()
                DashboardScreen(
                    projects = projects,
                    onCreateProjectClick = {
                        dashboardViewModel.createProject("New Project") { projId ->
                            onNavigateToEditor(projId)
                        }
                    },
                    onProjectClick = { projId ->
                        onNavigateToEditor(projId)
                    },
                    onAiToolsClick = onNavigateToAiScript
                )
            }
            composable("meme_hub") {
                MemeHubScreen()
            }
            composable("assets") {
                AssetsScreen()
            }
        }
    }
}

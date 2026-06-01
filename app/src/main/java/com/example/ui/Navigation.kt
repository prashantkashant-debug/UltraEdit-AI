package com.example.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.UltraEditApp
import com.example.ui.screens.AiScriptScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.MainScreen
import com.example.ui.screens.EditorScreen
import com.example.viewmodel.AiScriptViewModel
import com.example.viewmodel.DashboardViewModel
import com.example.viewmodel.DashboardViewModelFactory
import androidx.compose.ui.platform.LocalContext

@Composable
fun UltraEditNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val application = LocalContext.current.applicationContext as UltraEditApp
    
    val dashboardViewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModelFactory(application.repository)
    )

    val aiScriptViewModel: AiScriptViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "dashboard",
        modifier = modifier
    ) {
        composable("dashboard") {
            MainScreen(
                dashboardViewModel = dashboardViewModel,
                onNavigateToEditor = { projId ->
                    navController.navigate("editor/$projId")
                },
                onNavigateToAiScript = {
                    navController.navigate("ai_script")
                }
            )
        }
        
        composable("editor/{projectId}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId")?.toIntOrNull() ?: 0
            EditorScreen(
                projectId = projectId,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable("ai_script") {
            AiScriptScreen(
                viewModel = aiScriptViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

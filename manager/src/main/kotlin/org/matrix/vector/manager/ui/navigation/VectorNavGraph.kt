package org.matrix.vector.manager.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import org.matrix.vector.manager.ui.screens.home.HomeScreen
import org.matrix.vector.manager.ui.screens.modules.ModulesScreen
import org.matrix.vector.manager.ui.screens.modules.ScopeScreen

/**
 * Type-safe routing for the bottom navigation tabs.
 */
enum class MainRoute(val title: String) {
    Repo("Repo"),
    Modules("Modules"),
    Home("Home"),
    Logs("Logs"),
    Settings("Settings")
}

@Composable
fun VectorNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    // NavHost manages the swapping of screens
    NavHost(
        navController = navController,
        startDestination = MainRoute.Home.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(MainRoute.Repo.name) {
            PlaceholderScreen("Repository Screen")
        }
        composable(MainRoute.Modules.name) {
            ModulesScreen(
                onModuleClick = { packageName, userId ->
                    navController.navigate("Scope/$packageName/$userId")
                }
            )
        }
        composable("Scope/{packageName}/{userId}") { backStackEntry ->
            val pkg = backStackEntry.arguments?.getString("packageName") ?: return@composable
            val uid = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0

            ScopeScreen(
                packageName = pkg,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(MainRoute.Home.name) {
            HomeScreen()
        }
        composable(MainRoute.Logs.name) {
            PlaceholderScreen("Logs Screen")
        }
        composable(MainRoute.Settings.name) {
            PlaceholderScreen("Settings Screen")
        }
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title)
    }
}

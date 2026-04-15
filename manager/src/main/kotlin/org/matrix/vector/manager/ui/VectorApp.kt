package org.matrix.vector.manager.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Extension
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.matrix.vector.manager.ui.navigation.MainRoute
import org.matrix.vector.manager.ui.navigation.VectorNavGraph

/**
 * The main scaffolding of the app. It holds the Bottom Navigation Bar 
 * and hosts the Navigation Graph.
 */
@Composable
fun VectorApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val tabs = listOf(
        Triple(MainRoute.Repo, Icons.Rounded.Storefront, "Repo"),
        Triple(MainRoute.Modules, Icons.Rounded.Extension, "Modules"),
        Triple(MainRoute.Home, Icons.Rounded.Home, "Home"),
        Triple(MainRoute.Logs, Icons.Rounded.ReceiptLong, "Logs"),
        Triple(MainRoute.Settings, Icons.Rounded.Settings, "Settings")
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { (route, icon, label) ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentRoute == route.name,
                        onClick = {
                            if (currentRoute != route.name) {
                                navController.navigate(route.name) {
                                    // Pop to start destination so we don't build an infinite backstack
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // The NavGraph automatically pads itself so it doesn't draw under the bottom bar
        VectorNavGraph(navController = navController, innerPadding = innerPadding)
    }
}

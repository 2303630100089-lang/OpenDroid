package com.opendroid.shizustore.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.opendroid.shizustore.data.repository.AppCatalogRepository
import com.opendroid.shizustore.installer.InstallerManager
import com.opendroid.shizustore.ui.screen.AppDetailScreen
import com.opendroid.shizustore.ui.screen.HomeScreen
import com.opendroid.shizustore.ui.screen.SearchScreen

@Composable
fun ShizuStoreNavHost(
    repository: AppCatalogRepository,
    installerManager: InstallerManager
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomItem(NavRoutes.HOME, "Home", Icons.Rounded.Home),
        BottomItem(NavRoutes.SEARCH, "Search", Icons.Rounded.Search)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = NavRoutes.HOME) {
            composable(NavRoutes.HOME) {
                HomeScreen(
                    contentPadding = padding,
                    repository = repository,
                    installerManager = installerManager,
                    onOpenApp = { packageName -> navController.navigate("${NavRoutes.APP}/$packageName") }
                )
            }
            composable(NavRoutes.SEARCH) {
                SearchScreen(
                    contentPadding = padding,
                    repository = repository,
                    onOpenApp = { packageName -> navController.navigate("${NavRoutes.APP}/$packageName") }
                )
            }
            composable(
                route = "${NavRoutes.APP}/{packageName}",
                arguments = listOf(navArgument("packageName") { type = NavType.StringType })
            ) { backStackEntry ->
                AppDetailScreen(
                    contentPadding = padding,
                    repository = repository,
                    packageName = backStackEntry.arguments?.getString("packageName").orEmpty()
                )
            }
        }
    }
}

data class BottomItem(val route: String, val label: String, val icon: ImageVector)

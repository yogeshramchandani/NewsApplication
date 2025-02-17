    package com.example.realtimenewsapplication

    import com.example.realtimenewsapplication.screens.FavoriteArticlesScreen
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Icon
    import androidx.compose.material3.NavigationBar
    import androidx.compose.material3.NavigationBarItem
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.res.painterResource
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.currentBackStackEntryAsState
    import androidx.navigation.navArgument
    import com.example.realtimenewsapplication.screens.DetailedNewsScreen
    import com.example.realtimenewsapplication.screens.HomeScreen
    import com.example.realtimenewsapplication.screens.SearchScreen
    import com.example.realtimenewsapplication.util.Constants

    @Composable
    fun NavHostContainer(
        navController: NavHostController,
        padding: PaddingValues,
    ) {
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues = padding),
        ) {
            composable(route = "home") {
                HomeScreen(navController)
            }
            composable(route = "profile") {
                FavoriteArticlesScreen(
                    navController = navController
                )
            }
            composable(route = "search") {
                SearchScreen(navController)
            }
            composable(
                route = "detailScreen/{article}",
                arguments = listOf(navArgument("article") { defaultValue = "" }) // Define argument
            ) { backStackEntry ->
                DetailedNewsScreen(
                    backStackEntry,
                )
            }

        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBar {
            Constants.BottomNavItems.forEach { item ->
                NavigationBarItem(selected = currentRoute == item.route, onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }, label = {
                    Text(text = item.label)
                }, icon = {
                    Icon(   // Corrected Icon Usage
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label
                    )
                }
                )
            }

        }
    }

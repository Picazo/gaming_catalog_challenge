package com.gamingcatalog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gamingcatalog.ui.screen.catalog.CatalogScreen
import com.gamingcatalog.ui.screen.catalog.model.GameUI
import com.gamingcatalog.ui.screen.detail.DetailScreen
import com.google.gson.Gson
import java.net.URLDecoder

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.CatalogScreen.route,
        modifier = modifier,
    ) {

        composable(NavigationScreen.CatalogScreen.route) {
            CatalogScreen(
                onGameClick = { game ->
                    navController.navigate(NavigationScreen.DetailScreen.createRoute(game))
                }
            )
        }

        composable(
            route = NavigationScreen.DetailScreen.route,
            arguments = listOf(
                navArgument("gameJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val gameJson = backStackEntry.arguments?.getString("gameJson") ?: return@composable
            val decodedJson = URLDecoder.decode(gameJson, "UTF-8")
            val game = Gson().fromJson(decodedJson, GameUI::class.java)

            DetailScreen(
                game = game,
                onBackPressed = { navController.navigateUp() },
            )
        }
    }
}

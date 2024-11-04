package com.gamingcatalog.ui.navigation

import com.gamingcatalog.ui.screen.catalog.model.GameUI
import com.google.gson.Gson
import java.net.URLEncoder

sealed class NavigationScreen(
    val route: String,
    val baseRoute: String,
) {
    object CatalogScreen : NavigationScreen(
        route = "catalog_screen",
        baseRoute = "catalog_screen",
    )

    object DetailScreen : NavigationScreen(
        route = "detail_screen/{gameJson}",
        baseRoute = "detail_screen",
    ) {
        fun createRoute(game: GameUI): String {
            val gameJson = Gson().toJson(game)
            val encodedJson = URLEncoder.encode(gameJson, "UTF-8")
            return "detail_screen/$encodedJson"
        }
    }
}

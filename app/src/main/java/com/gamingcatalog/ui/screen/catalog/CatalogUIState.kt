package com.gamingcatalog.ui.screen.catalog

import com.gamingcatalog.ui.screen.catalog.model.GameUI

data class CatalogUIState(
    val loading: Boolean = false,
    val games: List<GameUI> = emptyList(),
    val filteredGames: List<GameUI> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val categories: List<String> = emptyList(),
    val error: String? = ""
)

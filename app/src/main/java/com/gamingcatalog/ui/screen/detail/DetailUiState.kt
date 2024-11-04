package com.gamingcatalog.ui.screen.detail

import com.gamingcatalog.ui.screen.catalog.model.GameUI


data class DetailUiState(
    val isShowingDeleteDialog: Boolean = false,
    val isDeleted: Boolean = false,
    val isEditing: Boolean = false,
    val editedGame: GameUI? = null,
    val isShowingSaveDialog: Boolean = false,
    val error: String? = null
)

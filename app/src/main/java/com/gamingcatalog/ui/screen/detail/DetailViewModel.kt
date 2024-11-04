package com.gamingcatalog.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamingcatalog.ui.screen.catalog.mapper.toDomain
import com.gamingcatalog.ui.screen.catalog.mapper.toUI
import com.gamingcatalog.ui.screen.catalog.model.GameUI
import com.juliopicazo.domain.interactor.DeleteGameUseCase
import com.juliopicazo.domain.interactor.FilterGamesUseCase
import com.juliopicazo.domain.interactor.GetCatalogUseCase
import com.juliopicazo.domain.interactor.UpdateGameUseCase
import com.juliopicazo.domain.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val deleteGameUseCase: DeleteGameUseCase,
    private val updateGameUseCase: UpdateGameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    fun onEditClick(game: GameUI) {
        _uiState.update { it.copy(
            isEditing = true,
            editedGame = game
        )}
    }

    fun onCancelEdit() {
        _uiState.update { it.copy(
            isEditing = false,
            editedGame = null
        )}
    }

    fun onUpdateField(field: String, value: String) {
        val currentGame = _uiState.value.editedGame ?: return
        val updatedGame = when (field) {
            "shortDescription" -> currentGame.copy(shortDescription = value)
            "developer" -> currentGame.copy(developer = value)
            "publisher" -> currentGame.copy(publisher = value)
            "platform" -> currentGame.copy(platform = value)
            "genre" -> currentGame.copy(genre = value)
            else -> currentGame
        }
        _uiState.update { it.copy(editedGame = updatedGame) }
    }

    fun onShowSaveDialog() {
        _uiState.update { it.copy(isShowingSaveDialog = true) }
    }

    fun onDismissSaveDialog() {
        _uiState.update { it.copy(isShowingSaveDialog = false) }
    }

    fun onSaveChanges() {
        val gameToUpdate = _uiState.value.editedGame ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateGameUseCase(gameToUpdate.toDomain())
                _uiState.update {
                    it.copy(
                        isEditing = false,
                        editedGame = gameToUpdate,
                        isShowingSaveDialog = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message,
                        isShowingSaveDialog = false
                    )
                }
            }
        }
    }

    fun onDeleteClick() {
        _uiState.update { it.copy(isShowingDeleteDialog = true) }
    }

    fun onDismissDeleteDialog() {
        _uiState.update { it.copy(isShowingDeleteDialog = false) }
    }

    fun onConfirmDelete(gameId: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isShowingDeleteDialog = false) }
                deleteGameUseCase(gameId)
                _uiState.update { it.copy(isDeleted = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message ?: "Error desconocido")
                }
            }
        }
    }

}
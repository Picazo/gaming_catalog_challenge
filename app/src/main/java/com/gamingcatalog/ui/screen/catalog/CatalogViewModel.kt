package com.gamingcatalog.ui.screen.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamingcatalog.ui.screen.catalog.mapper.toUI
import com.juliopicazo.domain.interactor.FilterGamesUseCase
import com.juliopicazo.domain.interactor.GetCatalogUseCase
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
class CatalogViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val filterGamesUseCase: FilterGamesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogUIState())
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun getCatalog() {
        viewModelScope.launch(Dispatchers.IO) {
            getCatalogUseCase().collectLatest { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        val games = result.data ?: emptyList()
                        val categories = games.map { it.genre.trim() }.distinct().sortedBy { it }

                        _uiState.update {
                            it.copy(
                                loading = false,
                                games = games.map { game -> game.toUI() },
                                filteredGames = games.map { game -> game.toUI() },
                                categories = listOf("All") + categories
                            )
                        }
                    }

                    Status.LOADING -> {
                        _uiState.update {
                            it.copy(loading = true)
                        }
                    }

                    Status.ERROR -> {
                        _uiState.update {
                            it.copy(loading = false)
                        }
                    }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.update { it.copy(searchQuery = query) }
            delay(300)
            filterGames()
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.update {
            it.copy(selectedCategory = if (category == "All") null else category)
        }
        filterGames()
    }

    private fun filterGames() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _uiState.value
            filterGamesUseCase(
                query = currentState.searchQuery,
                category = currentState.selectedCategory
            ).collectLatest { filteredGames ->
                _uiState.update {
                    it.copy(filteredGames = filteredGames.map { game -> game.toUI() })
                }
            }
        }
    }
}
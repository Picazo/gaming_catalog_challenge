package com.juliopicazo.domain.repository

import com.juliopicazo.domain.model.Game
import com.juliopicazo.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GameCatalogRepository {
    suspend fun getCatalog(): Flow<Resource<List<Game>>>
    suspend fun filterGames(query: String, category: String?): Flow<List<Game>>
    suspend fun updateGame(game: Game)
}
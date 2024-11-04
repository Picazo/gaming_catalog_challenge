package com.juliopicazo.domain.datasource.local

import com.juliopicazo.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getLocalGames(): Flow<List<Game>>
    suspend fun saveGames(games: List<Game>)
    suspend fun updateGame(game: Game)
}
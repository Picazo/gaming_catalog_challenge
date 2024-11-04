package com.juliopicazo.data.repository

import com.juliopicazo.data.database.dao.GameDao
import com.juliopicazo.data.database.mapper.toDomain
import com.juliopicazo.data.database.mapper.toEntity
import com.juliopicazo.domain.datasource.local.LocalDataSource
import com.juliopicazo.domain.model.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val gameDao: GameDao
) : LocalDataSource {
    override fun getLocalGames(): Flow<List<Game>> =
        gameDao.getGameCatalog().map { entities -> entities.map { it.toDomain() } }

    override suspend fun saveGames(games: List<Game>) {
        gameDao.insertGameCatalog(games.map { it.toEntity() })
    }

    override suspend fun updateGame(game: Game) {
        gameDao.updateGame(game.toEntity())
    }
}

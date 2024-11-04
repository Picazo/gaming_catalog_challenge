package com.juliopicazo.data.database

import com.juliopicazo.data.database.dao.GameDao
import com.juliopicazo.domain.repository.local.GameCatalogDBRepository

class GameCatalogDBRepositoryImpl(
    private val gameDao: GameDao,
) : GameCatalogDBRepository {

    override suspend fun deleteGame(gameId: Int) {
        gameDao.deleteGame(gameId)
    }


}
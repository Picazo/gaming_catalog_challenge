package com.juliopicazo.domain.repository.local


interface GameCatalogDBRepository {
    suspend fun deleteGame(gameId: Int)
}
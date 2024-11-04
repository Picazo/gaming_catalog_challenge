package com.juliopicazo.domain.interactor

import com.juliopicazo.domain.repository.local.GameCatalogDBRepository
import javax.inject.Inject

class DeleteGameUseCaseImpl @Inject constructor(
    private val gameCatalogDBRepository: GameCatalogDBRepository
) : DeleteGameUseCase {
    override suspend operator fun invoke(gameId: Int) {
        gameCatalogDBRepository.deleteGame(gameId)
    }
}
package com.juliopicazo.domain.interactor

import com.juliopicazo.domain.model.Game
import com.juliopicazo.domain.repository.GameCatalogRepository
import javax.inject.Inject

class UpdateGameUseCaseImpl @Inject constructor(
    private val gameCatalogRepository: GameCatalogRepository
) : UpdateGameUseCase {
    override suspend operator fun invoke(game: Game) {
        gameCatalogRepository.updateGame(game)
    }
}
package com.juliopicazo.domain.interactor

import com.juliopicazo.domain.model.Game

interface UpdateGameUseCase {
    suspend operator fun invoke(game: Game)
}
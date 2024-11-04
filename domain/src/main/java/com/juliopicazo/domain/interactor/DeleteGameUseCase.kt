package com.juliopicazo.domain.interactor

interface DeleteGameUseCase {
    suspend operator fun invoke(gameId: Int)
}
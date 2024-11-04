package com.juliopicazo.domain.interactor

import com.juliopicazo.domain.repository.GameCatalogRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilterGamesUseCaseImpl @Inject constructor(
    private val repository: GameCatalogRepository
) : FilterGamesUseCase {
    override suspend operator fun invoke(query: String, category: String?) =
        flow {
            emitAll(repository.filterGames(query, category))
        }

}
package com.juliopicazo.domain.interactor

import com.juliopicazo.domain.repository.GameCatalogRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCatalogUseCaseImpl @Inject constructor(
    private val gameCatalogRepository: GameCatalogRepository,
) : GetCatalogUseCase {
    override suspend operator fun invoke() =
        flow {
            emitAll(gameCatalogRepository.getCatalog())
        }
}
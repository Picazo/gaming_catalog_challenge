package com.juliopicazo.domain.interactor

import com.juliopicazo.domain.model.Game
import com.juliopicazo.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetCatalogUseCase {
    suspend operator fun invoke(): Flow<Resource<List<Game>>>
}
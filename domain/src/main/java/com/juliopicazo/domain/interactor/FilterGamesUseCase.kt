package com.juliopicazo.domain.interactor

import com.juliopicazo.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface FilterGamesUseCase {
    suspend operator fun invoke(query: String, category: String?): Flow<List<Game>>
}
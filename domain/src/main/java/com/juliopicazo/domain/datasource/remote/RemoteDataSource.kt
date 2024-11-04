package com.juliopicazo.domain.datasource.remote

import com.juliopicazo.domain.model.Game

interface RemoteDataSource {
    suspend fun fetchCatalog(): List<Game>
}
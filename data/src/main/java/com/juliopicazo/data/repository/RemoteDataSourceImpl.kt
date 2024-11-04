package com.juliopicazo.data.repository

import com.juliopicazo.data.mapper.toDomain
import com.juliopicazo.data.networking.GameCatalogApi
import com.juliopicazo.domain.datasource.remote.RemoteDataSource
import com.juliopicazo.domain.model.Game
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val gameCatalogApi: GameCatalogApi
) : RemoteDataSource {
    override suspend fun fetchCatalog(): List<Game> {
        val response = gameCatalogApi.getGameCatalog()
        return if (response.isSuccessful) {
            response.body()?.map { it.toDomain() } ?: emptyList()
        } else {
            emptyList()
        }
    }
}

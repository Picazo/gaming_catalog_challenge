package com.juliopicazo.data.networking

import com.juliopicazo.data.networking.model.GameCatalogResponse
import retrofit2.Response
import retrofit2.http.GET

interface GameCatalogApi {
    @GET("games")
    suspend fun getGameCatalog(): Response<List<GameCatalogResponse>>
}
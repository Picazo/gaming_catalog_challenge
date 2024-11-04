package com.juliopicazo.data.mapper

import com.juliopicazo.data.networking.model.GameCatalogResponse
import com.juliopicazo.domain.model.Game

fun GameCatalogResponse.toDomain() =
    Game(
        id = id,
        title = title,
        thumbnail = thumbnail,
        shortDescription = shortDescription,
        gameUrl = gameUrl,
        genre = genre,
        platform = platform,
        publisher = publisher,
        developer = developer,
        releaseDate = releaseDate,
        freeToGameProfileUrl = freeToGameProfileUrl
    )
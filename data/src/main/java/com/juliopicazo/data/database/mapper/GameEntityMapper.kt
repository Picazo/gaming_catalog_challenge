package com.juliopicazo.data.database.mapper

import com.juliopicazo.data.database.entity.GameEntity
import com.juliopicazo.domain.model.Game

fun GameEntity.toDomain() = Game(
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

fun Game.toEntity() = GameEntity(
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
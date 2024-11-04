package com.gamingcatalog.ui.screen.catalog.mapper

import com.gamingcatalog.ui.screen.catalog.model.GameUI
import com.juliopicazo.data.database.entity.GameEntity
import com.juliopicazo.domain.model.Game

fun Game.toUI() =
    GameUI(
        id = id,
        title = title,
        thumbnail = thumbnail,
        shortDescription = shortDescription,
        genre = genre,
        platform = platform,
        publisher = publisher,
        developer = developer,
    )

fun GameUI.toDomain() = Game(
    id = id,
    title = title,
    thumbnail = thumbnail,
    shortDescription = shortDescription,
    genre = genre,
    platform = platform,
    publisher = publisher,
    developer = developer,
    gameUrl = "",
    releaseDate = "",
    freeToGameProfileUrl = "",
)
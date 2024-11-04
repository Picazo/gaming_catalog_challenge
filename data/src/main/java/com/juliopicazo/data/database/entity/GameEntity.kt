package com.juliopicazo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val thumbnail: String,
    val shortDescription: String,
    val gameUrl: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val releaseDate: String,
    val freeToGameProfileUrl: String,
    val isActive: Boolean = true
)
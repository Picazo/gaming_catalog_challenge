package com.juliopicazo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juliopicazo.data.database.dao.GameDao
import com.juliopicazo.data.database.entity.GameEntity

@Database(
    entities = [
        GameEntity::class,
    ],
    version = 1
)
abstract class GameCatalogDB : RoomDatabase() {
    abstract fun gameDao(): GameDao
}
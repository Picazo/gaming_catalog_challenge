package com.juliopicazo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.juliopicazo.data.database.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameCatalog(games: List<GameEntity>)

    @Transaction
    @Query("SELECT * FROM games WHERE isActive = 1")
    fun getGameCatalog(): Flow<List<GameEntity>>

    @Query("UPDATE games SET isActive = 0 WHERE id = :gameId")
    suspend fun deleteGame(gameId: Int)

    @Update
    suspend fun updateGame(game: GameEntity)

}
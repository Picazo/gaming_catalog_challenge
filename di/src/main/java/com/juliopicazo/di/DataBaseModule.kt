package com.juliopicazo.di

import android.content.Context
import androidx.room.Room
import com.juliopicazo.data.database.GameCatalogDB
import com.juliopicazo.data.database.GameCatalogDBRepositoryImpl
import com.juliopicazo.data.database.dao.GameDao
import com.juliopicazo.domain.repository.local.GameCatalogDBRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    private const val DB = "GameCatalogDBRepository"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): GameCatalogDB {
        return Room
            .databaseBuilder(context, GameCatalogDB::class.java, DB)
            .build()
    }

    @Provides
    @Singleton
    fun provideGameCatalogDao(gameCatalogDB: GameCatalogDB): GameDao = gameCatalogDB.gameDao()

    @Provides
    fun provideGameCatalogDBRepository(
        gameDao: GameDao,
    ): GameCatalogDBRepository =
        GameCatalogDBRepositoryImpl(
            gameDao = gameDao
        )


}

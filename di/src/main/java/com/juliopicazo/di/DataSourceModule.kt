package com.juliopicazo.di

import com.juliopicazo.data.database.dao.GameDao
import com.juliopicazo.data.networking.GameCatalogApi
import com.juliopicazo.data.repository.LocalDataSourceImpl
import com.juliopicazo.data.repository.RemoteDataSourceImpl
import com.juliopicazo.domain.datasource.local.LocalDataSource
import com.juliopicazo.domain.datasource.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(gameCatalogApi: GameCatalogApi): RemoteDataSource =
        RemoteDataSourceImpl(gameCatalogApi)

    @Singleton
    @Provides
    fun provideLocalDataSource(gameDao: GameDao): LocalDataSource =
        LocalDataSourceImpl(gameDao)
}

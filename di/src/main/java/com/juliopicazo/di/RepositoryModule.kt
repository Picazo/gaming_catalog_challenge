package com.juliopicazo.di

import com.juliopicazo.data.repository.GameCatalogRepositoryImpl
import com.juliopicazo.domain.datasource.local.LocalDataSource
import com.juliopicazo.domain.datasource.remote.RemoteDataSource
import com.juliopicazo.domain.repository.GameCatalogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideGameCatalogRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): GameCatalogRepository =
        GameCatalogRepositoryImpl(remoteDataSource, localDataSource)
}

package com.juliopicazo.di

import com.juliopicazo.domain.interactor.DeleteGameUseCase
import com.juliopicazo.domain.interactor.DeleteGameUseCaseImpl
import com.juliopicazo.domain.interactor.FilterGamesUseCase
import com.juliopicazo.domain.interactor.FilterGamesUseCaseImpl
import com.juliopicazo.domain.interactor.GetCatalogUseCase
import com.juliopicazo.domain.interactor.GetCatalogUseCaseImpl
import com.juliopicazo.domain.interactor.UpdateGameUseCase
import com.juliopicazo.domain.interactor.UpdateGameUseCaseImpl
import com.juliopicazo.domain.repository.GameCatalogRepository
import com.juliopicazo.domain.repository.local.GameCatalogDBRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object InteractionModule {
    @Provides
    fun provideGetCatalogUseCase(gameCatalogRepository: GameCatalogRepository): GetCatalogUseCase =
        GetCatalogUseCaseImpl(gameCatalogRepository)

    @Provides
    fun provideFilterGamesUseCase(gameCatalogRepository: GameCatalogRepository): FilterGamesUseCase {
        return FilterGamesUseCaseImpl(gameCatalogRepository)
    }

    @Provides
    fun provideDeleteGameUseCase(gameCatalogDBRepository: GameCatalogDBRepository): DeleteGameUseCase {
        return DeleteGameUseCaseImpl(gameCatalogDBRepository)
    }

    @Provides
    fun provideUpdateGameUseCase(gameCatalogRepository: GameCatalogRepository): UpdateGameUseCase {
        return UpdateGameUseCaseImpl(gameCatalogRepository)
    }
}

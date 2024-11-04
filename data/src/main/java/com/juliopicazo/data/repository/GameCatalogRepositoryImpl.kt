package com.juliopicazo.data.repository


import com.juliopicazo.domain.datasource.local.LocalDataSource
import com.juliopicazo.domain.datasource.remote.RemoteDataSource
import com.juliopicazo.domain.model.Game
import com.juliopicazo.domain.repository.GameCatalogRepository
import com.juliopicazo.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameCatalogRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : GameCatalogRepository {


    override suspend fun getCatalog(): Flow<Resource<List<Game>>> = channelFlow {
        send(Resource.loading())
        launch {
            localDataSource.getLocalGames().collectLatest { localGames ->
                if (localGames.isNotEmpty()) {
                    send(Resource.success(localGames))
                } else {
                    try {
                        val remoteGames = remoteDataSource.fetchCatalog()
                        localDataSource.saveGames(remoteGames)
                        send(Resource.success(remoteGames))
                    } catch (e: Exception) {
                        send(Resource.error(e.message ?: "Error fetching catalog"))
                    }
                }
            }
        }
    }

    override suspend fun filterGames(query: String, category: String?): Flow<List<Game>> {
        return localDataSource.getLocalGames().map { games ->
            games.filter { game ->
                game.title.contains(query, ignoreCase = true) &&
                        (category?.let { it == game.genre } ?: true)
            }
        }
    }

    override suspend fun updateGame(game: Game) {
        localDataSource.updateGame(game)
    }

}
package com.gamingcatalog

import com.gamingcatalog.ui.screen.catalog.CatalogViewModel
import com.juliopicazo.domain.interactor.FilterGamesUseCase
import com.juliopicazo.domain.interactor.GetCatalogUseCase
import com.juliopicazo.domain.model.Game
import com.juliopicazo.domain.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatalogViewModelTest {

    private lateinit var viewModel: CatalogViewModel
    private lateinit var getCatalogUseCase: GetCatalogUseCase
    private lateinit var filterGamesUseCase: FilterGamesUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val sampleGames = listOf(
        Game(
            id = 1,
            title = "Game 1",
            thumbnail = "https://www.freetogame.com/g/1/thumbnail.jpg",
            shortDescription = "An action-packed adventure game with stunning graphics",
            gameUrl = "https://www.freetogame.com/open/game1",
            genre = "Action",
            platform = "PC",
            publisher = "Publisher 1",
            developer = "Developer 1",
            releaseDate = "2023-01-15",
            freeToGameProfileUrl = "https://www.freetogame.com/game1-profile"
        ),
        Game(
            id = 2,
            title = "Game 2",
            thumbnail = "https://www.freetogame.com/g/2/thumbnail.jpg",
            shortDescription = "Epic RPG with immersive storytelling",
            gameUrl = "https://www.freetogame.com/open/game2",
            genre = "RPG",
            platform = "PS5",
            publisher = "Publisher 2",
            developer = "Developer 2",
            releaseDate = "2023-03-22",
            freeToGameProfileUrl = "https://www.freetogame.com/game2-profile"
        ),
        Game(
            id = 3,
            title = "Game 3",
            thumbnail = "https://www.freetogame.com/g/3/thumbnail.jpg",
            shortDescription = "Competitive multiplayer shooter",
            gameUrl = "https://www.freetogame.com/open/game3",
            genre = "Shooter",
            platform = "PC",
            publisher = "Publisher 3",
            developer = "Developer 3",
            releaseDate = "2023-06-10",
            freeToGameProfileUrl = "https://www.freetogame.com/game3-profile"
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCatalogUseCase = mockk()
        filterGamesUseCase = mockk()
        viewModel = CatalogViewModel(getCatalogUseCase, filterGamesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCatalog success loads games and categories correctly`() = runTest {
        // Given
        coEvery { getCatalogUseCase() } returns flowOf(Resource.success(sampleGames))
        coEvery { filterGamesUseCase(any(), any()) } returns flowOf(sampleGames)

        // When
        viewModel.getCatalog()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertEquals(false, currentState.loading)
        assertEquals(sampleGames.size, currentState.games.size)
        assertEquals(listOf("All", "Action", "RPG", "Shooter"), currentState.categories)
        assertEquals(sampleGames.size, currentState.filteredGames.size)
    }

    @Test
    fun `getCatalog loading shows loading state`() = runTest {
        // Given
        coEvery { getCatalogUseCase() } returns flowOf(Resource.loading())

        // When
        viewModel.getCatalog()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value.loading)
    }

    @Test
    fun `onSearchQueryChange updates search query and filters games`() = runTest {
        // Given
        val searchQuery = "Game 1"
        val expectedFilteredGames = listOf(sampleGames[0])
        coEvery { filterGamesUseCase(any(), any()) } returns flowOf(expectedFilteredGames)

        // When
        viewModel.onSearchQueryChange(searchQuery)
        testDispatcher.scheduler.advanceTimeBy(301) // Avanzamos más del debounce
        testDispatcher.scheduler.runCurrent()

        // Then
        val currentState = viewModel.uiState.value
        assertEquals(searchQuery, currentState.searchQuery)
        assertEquals(expectedFilteredGames, currentState.filteredGames)
    }

    @Test
    fun `onCategorySelected updates category and filters games correctly`() = runTest {
        // Given
        val category = "Action"
        val expectedFilteredGames = listOf(sampleGames[0])
        coEvery { filterGamesUseCase(any(), eq(category)) } returns flowOf(expectedFilteredGames)

        // When
        viewModel.onCategorySelected(category)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertEquals(category, currentState.selectedCategory)
        assertEquals(expectedFilteredGames, currentState.filteredGames)
    }

    @Test
    fun `onCategorySelected with All sets selectedCategory to null`() = runTest {
        // Given
        coEvery { filterGamesUseCase(any(), null) } returns flowOf(sampleGames)

        // When
        viewModel.onCategorySelected("All")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertEquals(null, currentState.selectedCategory)
        assertEquals(sampleGames, currentState.filteredGames)
    }

    @Test
    fun `getCatalog error maintains current state`() = runTest {
        // Given
        coEvery { getCatalogUseCase() } returns flowOf(Resource.error("Error"))

        // When
        viewModel.getCatalog()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertEquals(false, currentState.loading)
        assertTrue(currentState.games.isEmpty())
        assertTrue(currentState.filteredGames.isEmpty())
    }
}
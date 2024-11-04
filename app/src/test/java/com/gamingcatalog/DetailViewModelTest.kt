package com.gamingcatalog

import com.gamingcatalog.ui.screen.catalog.model.GameUI
import com.gamingcatalog.ui.screen.detail.DetailViewModel
import com.juliopicazo.domain.interactor.DeleteGameUseCase
import com.juliopicazo.domain.interactor.UpdateGameUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var deleteGameUseCase: DeleteGameUseCase
    private lateinit var updateGameUseCase: UpdateGameUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val mockGame = GameUI(
        id = 1,
        title = "Test Game",
        thumbnail = "url",
        shortDescription = "description",
        genre = "Action",
        platform = "PC",
        publisher = "Publisher",
        developer = "Developer"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        deleteGameUseCase = mockk()
        updateGameUseCase = mockk()
        viewModel = DetailViewModel(deleteGameUseCase, updateGameUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onEditClick updates state correctly`() = runTest {
        // When
        viewModel.onEditClick(mockGame)

        // Then
        val currentState = viewModel.uiState.value
        assertTrue(currentState.isEditing)
        assertEquals(mockGame, currentState.editedGame)
    }

    @Test
    fun `onCancelEdit resets editing state`() = runTest {
        // Given
        viewModel.onEditClick(mockGame)

        // When
        viewModel.onCancelEdit()

        // Then
        val currentState = viewModel.uiState.value
        assertFalse(currentState.isEditing)
        assertNull(currentState.editedGame)
    }

    @Test
    fun `onUpdateField updates specific field correctly`() = runTest {
        // Given
        viewModel.onEditClick(mockGame)

        // When
        viewModel.onUpdateField("shortDescription", "New Description")

        // Then
        val currentState = viewModel.uiState.value
        assertEquals("New Description", currentState.editedGame?.shortDescription)
    }

    @Test
    fun `onShowSaveDialog shows save dialog`() = runTest {
        // When
        viewModel.onShowSaveDialog()

        // Then
        assertTrue(viewModel.uiState.value.isShowingSaveDialog)
    }

    @Test
    fun `onDismissSaveDialog hides save dialog`() = runTest {
        // Given
        viewModel.onShowSaveDialog()

        // When
        viewModel.onDismissSaveDialog()

        // Then
        assertFalse(viewModel.uiState.value.isShowingSaveDialog)
    }

    @Test
    fun `onSaveChanges updates game successfully`() = runTest {
        // Given
        coEvery { updateGameUseCase(any()) } returns Unit
        viewModel.onEditClick(mockGame)

        // When
        viewModel.onSaveChanges()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertFalse(currentState.isEditing)
        assertNotNull(currentState.editedGame)
        assertFalse(currentState.isShowingSaveDialog)
        coVerify { updateGameUseCase(any()) }
    }

    @Test
    fun `onSaveChanges handles error`() = runTest {
        // Given
        val errorMessage = "Update failed"
        coEvery { updateGameUseCase(any()) } throws Exception(errorMessage)
        viewModel.onEditClick(mockGame)

        // When
        viewModel.onSaveChanges()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertEquals(errorMessage, currentState.error)
        assertFalse(currentState.isShowingSaveDialog)
    }

    @Test
    fun `onDeleteClick shows delete dialog`() = runTest {
        // When
        viewModel.onDeleteClick()

        // Then
        assertTrue(viewModel.uiState.value.isShowingDeleteDialog)
    }

    @Test
    fun `onDismissDeleteDialog hides delete dialog`() = runTest {
        // Given
        viewModel.onDeleteClick()

        // When
        viewModel.onDismissDeleteDialog()

        // Then
        assertFalse(viewModel.uiState.value.isShowingDeleteDialog)
    }

    @Test
    fun `onConfirmDelete deletes game successfully`() = runTest {
        // Given
        coEvery { deleteGameUseCase(any()) } returns Unit

        // When
        viewModel.onConfirmDelete(1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertTrue(currentState.isDeleted)
        assertFalse(currentState.isShowingDeleteDialog)
        coVerify { deleteGameUseCase(1) }
    }

    @Test
    fun `onConfirmDelete handles error`() = runTest {
        // Given
        val errorMessage = "Delete failed"
        coEvery { deleteGameUseCase(any()) } throws Exception(errorMessage)

        // When
        viewModel.onConfirmDelete(1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertEquals("Delete failed", currentState.error)
        assertFalse(currentState.isShowingDeleteDialog)
    }
}
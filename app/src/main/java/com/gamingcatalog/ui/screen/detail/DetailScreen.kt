package com.gamingcatalog.ui.screen.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gamingcatalog.ui.screen.catalog.model.GameUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    game: GameUI,
    onBackPressed: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()
    val imageHeight = 250.dp
    val imageHeightPx = with(LocalDensity.current) { imageHeight.toPx() }

    val imageCollapseFraction by remember {
        derivedStateOf {
            (scrollState.value / imageHeightPx).coerceIn(0f, 1f)
        }
    }


    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) {
            onBackPressed()
        }
    }

    if (uiState.isShowingSaveDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissSaveDialog() },
            title = { Text("Guardar cambios") },
            text = { Text("¿Deseas guardar los cambios realizados?") },
            confirmButton = {
                TextButton(onClick = { viewModel.onSaveChanges() }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onDismissSaveDialog() }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (uiState.isShowingDeleteDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissDeleteDialog() },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro que deseas borrar este juego?") },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.onConfirmDelete(game.id) }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.onDismissDeleteDialog() }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(game.title) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (uiState.isEditing) {
                        IconButton(onClick = { viewModel.onShowSaveDialog() }) {
                            Icon(Icons.Default.Done, contentDescription = "Save")
                        }
                        IconButton(onClick = { viewModel.onCancelEdit() }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel")
                        }
                    } else {
                        IconButton(onClick = { viewModel.onEditClick(game) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { viewModel.onDeleteClick() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            AsyncImage(
                model = game.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight * (1f - imageCollapseFraction)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                val currentGame = uiState.editedGame ?: game

                DetailSection(
                    title = "Descripción",
                    content = currentGame.shortDescription,
                    isEditing = uiState.isEditing,
                    onValueChange = { viewModel.onUpdateField("shortDescription", it) }
                )
                DetailSection(
                    title = "Desarrollador",
                    content = currentGame.developer,
                    isEditing = uiState.isEditing,
                    onValueChange = { viewModel.onUpdateField("developer", it) }
                )
                DetailSection(
                    title = "Publisher",
                    content = currentGame.publisher,
                    isEditing = uiState.isEditing,
                    onValueChange = { viewModel.onUpdateField("publisher", it) }
                )
                DetailSection(
                    title = "Plataforma",
                    content = currentGame.platform,
                    isEditing = false,
                    onValueChange = { viewModel.onUpdateField("platform", it) }
                )
                DetailSection(
                    title = "Género",
                    content = currentGame.genre,
                    isEditing = false,
                    onValueChange = { viewModel.onUpdateField("genre", it) }
                )
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: String,
    isEditing: Boolean = false,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        if (isEditing) {
            OutlinedTextField(
                value = content,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        } else {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
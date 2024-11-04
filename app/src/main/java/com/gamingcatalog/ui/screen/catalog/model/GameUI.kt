package com.gamingcatalog.ui.screen.catalog.model

import java.io.Serializable

data class GameUI(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val shortDescription: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
) : Serializable

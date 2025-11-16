package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class JamendoResponse(
    val results: List<Track> = emptyList()
)

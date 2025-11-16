package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val id: String? = "",
    val name: String? = "",
    val artist_name: String? = "",
    val album_name: String? = "",
    val duration: Int? = 0,
    val audio: String? = "",
    val image: String? = ""
)

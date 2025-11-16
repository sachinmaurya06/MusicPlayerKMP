package com.example.network

import com.example.model.JamendoResponse
import com.example.model.Track
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class MusicApi {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val clientId = "cc571f21"

    suspend fun getTracks(): List<Track> {
        val url =
            "https://api.jamendo.com/v3.0/tracks/?client_id=$clientId&format=json&limit=20&include=musicinfo"

        val response = client.get(url).body<JamendoResponse>()
        return response.results
    }
}

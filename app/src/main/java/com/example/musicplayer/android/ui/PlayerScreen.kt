package com.example.musicplayer.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.model.Track
import com.example.musicplayer.android.player.MusicPlayer
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    track: Track,
    onBack: () -> Unit,
    musicPlayer: MusicPlayer
) {
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(track) {
        musicPlayer.play(track.audio ?: "")
        isPlaying = true
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            val duration = musicPlayer.getDuration()
            val current = musicPlayer.getCurrentPosition()

            if (duration > 0) {
                progress = current.toFloat() / duration.toFloat()
            }
            delay(300)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(track.name ?: "Now Playing") },
                navigationIcon = {
                    IconButton(onClick = {
                        musicPlayer.pause()
                        onBack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = track.image,
                contentDescription = track.name,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                track.name ?: "Unknown Title",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                track.artist_name ?: "Unknown Artist",
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Progress Slider
            Slider(
                value = progress,
                onValueChange = {
                    progress = it
                    musicPlayer.seekToPercent(it)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Play / Pause button
            Button(
                onClick = {
                    if (isPlaying) {
                        musicPlayer.pause()
                    } else {
                        musicPlayer.resume()
                    }
                    isPlaying = !isPlaying
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(55.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isPlaying) "Pause" else "Play")
            }
        }
    }
}

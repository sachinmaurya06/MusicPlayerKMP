package com.example.musicplayer.android.player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class MusicPlayer(private val context: Context) {

    private val player: ExoPlayer = ExoPlayer.Builder(context).build()

    // Play URL
    fun play(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    // Pause audio
    fun pause() {
        player.pause()
    }

    // Resume audio
    fun resume() {
        player.play()
    }

    // Stop + release resources
    fun release() {
        player.release()
    }

    // Duration in milliseconds
    fun getDuration(): Long {
        return player.duration.takeIf { it > 0 } ?: 0L
    }

    // Current position in milliseconds
    fun getCurrentPosition(): Long {
        return player.currentPosition
    }

    // Seek using percentage slider
    fun seekToPercent(percent: Float) {
        val duration = getDuration()
        if (duration > 0) {
            val newPos = (duration * percent).toLong()
            player.seekTo(newPos)
        }
    }
}

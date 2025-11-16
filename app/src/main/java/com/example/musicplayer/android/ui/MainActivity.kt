package com.example.musicplayer.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicplayer.android.player.MusicPlayer
import com.example.musicplayer.android.ui.MusicScreen
import com.example.musicplayer.android.ui.PlayerScreen
import com.example.viewmodel.MusicViewModel

class MainActivity : ComponentActivity() {

    private lateinit var musicPlayer: MusicPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Shared ExoPlayer instance
        musicPlayer = MusicPlayer(this)

        setContent {
            MaterialTheme {

                val navController = rememberNavController()
                val viewModel = MusicViewModel() // Shared ViewModel

                NavHost(
                    navController = navController,
                    startDestination = "music"
                ) {

                    // ─────────────────────────────
                    // MUSIC LIST SCREEN
                    // ─────────────────────────────
                    composable("music") {
                        MusicScreen(
                            viewModel = viewModel,
                            onTrackClick = { track ->
                                navController.navigate("player/${track.id}")
                            }
                        )
                    }

                    // ─────────────────────────────
                    // PLAYER SCREEN
                    // ─────────────────────────────
                    composable(
                        route = "player/{id}",
                        arguments = listOf(
                            navArgument("id") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->

                        val trackId = backStackEntry.arguments?.getString("id") ?: ""

                        // Read items OUTSIDE composition = no error
                        val trackList = viewModel.tracks.value
                        val selectedTrack = trackList.find { it.id == trackId }

                        if (selectedTrack != null) {
                            PlayerScreen(
                                track = selectedTrack,
                                onBack = { navController.popBackStack() },
                                musicPlayer = musicPlayer
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release() // Release player on exit
    }
}

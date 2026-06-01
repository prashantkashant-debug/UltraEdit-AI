package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.FilterBAndW
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    projectId: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedVideoUri = uri }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    LaunchedEffect(selectedVideoUri) {
        selectedVideoUri?.let {
            val mediaItem = MediaItem.fromUri(it)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Project #$projectId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = "Export")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (selectedVideoUri == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = { videoPickerLauncher.launch("video/*") }) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Import Video")
                        }
                    }
                } else {
                    AndroidView(
                        factory = { ctx ->
                            PlayerView(ctx).apply {
                                player = exoPlayer
                                useController = true
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Timeline", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(onClick = { /* Auto Captions stub */ }, contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)) {
                        Icon(Icons.Default.AutoFixHigh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Auto Captions")
                    }
                    IconButton(onClick = { exoPlayer.playWhenReady = !exoPlayer.playWhenReady }) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play/Pause")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.width(48.dp)) { Text("V1", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp))
                            .padding(4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (selectedVideoUri != null) {
                            Text("Main Video Track", color = MaterialTheme.colorScheme.onPrimaryContainer, style = MaterialTheme.typography.bodySmall)
                        } else {
                            Text("Empty", color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha=0.5f), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.width(48.dp)) { Text("A1", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(4.dp))
                            .padding(4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (selectedVideoUri != null) {
                            Text("Audio Track", color = MaterialTheme.colorScheme.onTertiaryContainer, style = MaterialTheme.typography.bodySmall)
                        } else {
                            Text("Empty", color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha=0.5f), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.width(48.dp)) { Text("vFx", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(4.dp))
                            .padding(4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text("Effects Track", color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha=0.5f), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            // Bottom Editing Toolbar
            androidx.compose.foundation.lazy.LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val tools = listOf(
                    "Edit" to androidx.compose.material.icons.Icons.Default.ContentCut,
                    "Speed" to androidx.compose.material.icons.Icons.Default.Speed,
                    "Audio" to androidx.compose.material.icons.Icons.Default.MusicNote,
                    "Filters" to androidx.compose.material.icons.Icons.Default.FilterBAndW,
                    "Canvas" to androidx.compose.material.icons.Icons.Default.Crop,
                    "AI Effects" to androidx.compose.material.icons.Icons.Default.AutoFixHigh
                )

                items(tools.size) { i ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = tools[i].second,
                            contentDescription = tools[i].first,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tools[i].first,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class Asset(val id: Int, val title: String, val duration: String?, val imageUrl: String)

val mockVideos = listOf(
    Asset(1, "Aerial City Night", "0:15", "https://picsum.photos/seed/vid1/400/250"),
    Asset(2, "Forest Drone", "0:20", "https://picsum.photos/seed/vid2/400/250"),
    Asset(3, "Ocean Waves", "0:10", "https://picsum.photos/seed/vid3/400/250"),
    Asset(4, "Cyberpunk Street", "0:08", "https://picsum.photos/seed/vid4/400/250")
)

val mockImages = listOf(
    Asset(5, "Mountain View", null, "https://picsum.photos/seed/img1/300/300"),
    Asset(6, "Office Workspace", null, "https://picsum.photos/seed/img2/300/300"),
    Asset(7, "Abstract Colors", null, "https://picsum.photos/seed/img3/300/300"),
    Asset(8, "Vintage Tech", null, "https://picsum.photos/seed/img4/300/300")
)

val mockMusic = listOf(
    Asset(9, "Lo-Fi Chill", "2:30", "https://picsum.photos/seed/mus1/200/200"),
    Asset(10, "Corporate Tech", "1:45", "https://picsum.photos/seed/mus2/200/200"),
    Asset(11, "Cinematic Epic", "3:15", "https://picsum.photos/seed/mus3/200/200"),
    Asset(12, "Upbeat Pop", "2:10", "https://picsum.photos/seed/mus4/200/200")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsScreen() {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Asset Library", fontWeight = FontWeight.Bold) }
        )
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search 4K videos, photos, music...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true
        )
        
        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                AssetSection("Stock Videos", "Millions of royalty-free 4K/HD clips", mockVideos, true)
            }
            item {
                AssetSection("Stock Images", "High-quality photos and vectors", mockImages, false)
            }
            item {
                AssetSection("Music Library", "Copyright-free background music", mockMusic, false)
            }
        }
    }
}

@Composable
fun AssetSection(title: String, subtitle: String, assets: List<Asset>, isVideo: Boolean) {
    Column {
        PaddingValues(horizontal = 16.dp).let { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(assets) { asset ->
                Card(
                    modifier = Modifier
                        .width(if (isVideo) 240.dp else 160.dp)
                        .height(if (isVideo) 140.dp else 160.dp),
                    onClick = { /* TODO */ }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = asset.imageUrl,
                            contentDescription = asset.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .background(Color.Black.copy(alpha = 0.6f))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = asset.title,
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        if (asset.duration != null) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Black.copy(alpha = 0.7f))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = asset.duration,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

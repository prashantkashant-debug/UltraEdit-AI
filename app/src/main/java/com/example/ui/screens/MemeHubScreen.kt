package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class Meme(val id: Int, val title: String, val imageUrl: String)

val mockMemes = listOf(
    Meme(1, "Cat Vibing", "https://picsum.photos/seed/cat/400/400"),
    Meme(2, "Distracted Boyfriend", "https://picsum.photos/seed/meme2/400/400"),
    Meme(3, "Roll Safe", "https://picsum.photos/seed/meme3/400/400"),
    Meme(4, "Drake Hotline Bling", "https://picsum.photos/seed/meme4/400/400"),
    Meme(5, "Two Buttons", "https://picsum.photos/seed/meme5/400/400"),
    Meme(6, "Change My Mind", "https://picsum.photos/seed/meme6/400/400"),
    Meme(7, "Disaster Girl", "https://picsum.photos/seed/meme7/400/400"),
    Meme(8, "Is This a Pigeon?", "https://picsum.photos/seed/meme8/400/400"),
    Meme(9, "Mocking Spongebob", "https://picsum.photos/seed/meme9/400/400"),
    Meme(10, "Woman Yelling", "https://picsum.photos/seed/meme10/400/400")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeHubScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Meme Hub", fontWeight = FontWeight.Bold) }
        )
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search trending memes, GIFs...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true
        )
        
        val categories = listOf("Trending", "Reactions", "Sound Effects", "Green Screen", "वायरल")
        
        ScrollableTabRow(
            selectedTabIndex = selectedCategory,
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 16.dp
        ) {
            categories.forEachIndexed { index, title ->
                Tab(
                    selected = index == selectedCategory,
                    onClick = { selectedCategory = index },
                    text = { Text(title) }
                )
            }
        }
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(mockMemes) { meme ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.9f),
                    onClick = { /* TODO */ }
                ) {
                    Column {
                        AsyncImage(
                            model = meme.imageUrl,
                            contentDescription = meme.title,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = meme.title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

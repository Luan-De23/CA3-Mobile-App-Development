package com.example.ca3_mobileapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ca3_mobileapps.ui.theme.CA3_MobileAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA3_MobileAppsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieApp(innerPadding)
                }
            }
        }
    }
}

// Data model representing a Film entity
data class Movie(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val director: String,
    val year: String
)

@Composable
fun MovieApp(paddingValues: PaddingValues) {

    // State Management: Film list and form inputs using Compose state observables
    val movieList = remember {
        mutableStateListOf(
            Movie(title = "Inception", director = "Christopher Nolan", year = "2010"),
            Movie(title = "The Matrix", director = "Wachowski Sisters", year = "1999"),
            Movie(title = "Interstellar", director = "Christopher Nolan", year = "2014"),
            Movie(title = "Parasite", director = "Bong Joon-ho", year = "2019"),
            Movie(title = "The Godfather", director = "Francis Ford Coppola", year = "1972")
        )
    }

    var titleInput by remember { mutableStateOf("") }
    var directorInput by remember { mutableStateOf("") }
    var yearInput by remember { mutableStateOf("") }

    // Main layout container: Combines system padding and custom padding
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // Respects system bars (StatusBar/NavigationBar)
            .padding(16.dp)
    ) {
        Text(
            text = "My Movie Playlist",
            style = MaterialTheme.typography.headlineMedium
        )

        // LIST VIEW (Modern equivalent of RecyclerView)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            items(movieList) { movie ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(movie.title, style = MaterialTheme.typography.titleLarge)
                            Text(
                                text = "${movie.director} • ${movie.year}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        // DELETE BUTTON: Removes the item from the observable list
                        IconButton(onClick = { movieList.remove(movie) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Movie",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // ADDITION FORM: Input fields and submission logic
        Column(modifier = Modifier.padding(top = 8.dp)) {
            OutlinedTextField(
                value = titleInput,
                onValueChange = { titleInput = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = directorInput,
                onValueChange = { directorInput = it },
                label = { Text("Director") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = yearInput,
                onValueChange = { yearInput = it },
                label = { Text("Year") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // Simple validation before adding to the list
                    if (titleInput.isNotBlank() && directorInput.isNotBlank()) {
                        movieList.add(
                            Movie(title = titleInput, director = directorInput, year = yearInput)
                        )
                        // Reset input fields
                        titleInput = ""; directorInput = ""; yearInput = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Add Movie")
            }
        }
    }
}
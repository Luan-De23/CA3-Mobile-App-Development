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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ca3_mobileapps.ui.theme.CA3_MobileAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA3_MobileAppsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "CA3",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}




data class Films(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val director: String,
    val year: String
)

@Composable
fun MovieApp() {
    // Estado da lista e dos inputs do formulário
    val filmList = remember {
        mutableStateListOf(
            Films(title = "Inception", director = "Christopher Nolan", year = "2010"),
            Films(title = "The Matrix", director = "Wachowski Sisters", year = "1999"),
            Films(title = "Interstellar", director = "Christopher Nolan", year = "2014"),
            Films(title = "Parasite", director = "Bong Joon-ho", year = "2019"),
            Films(title = "The Godfather", director = "Francis Ford Coppola", year = "1972")
        )
    }
    var titleInput by remember { mutableStateOf("") }
    var directorInput by remember { mutableStateOf("") }
    var yearInput by remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Minha Playlist de Filmes", style = MaterialTheme.typography.headlineMedium)

        // 1. LISTAGEM (RecyclerView equivalente)
        LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 8.dp)) {
            items(filmList) { filme ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(filme.title, style = MaterialTheme.typography.titleLarge)
                            Text("${filme.director} • ${filme.year}", style = MaterialTheme.typography.bodyMedium)
                        }
                        // BOTÃO DELETAR (Vermelho com Ícone)
                        IconButton(onClick = { filmList.remove(filme) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Deletar", tint = Color.Red)
                        }
                    }
                }
            }
        }

        HorizontalDivider()

        // 2. FORMULÁRIO DE ADIÇÃO (3 Atributos)
        Column(modifier = Modifier.padding(top = 16.dp)) {
            OutlinedTextField(value = titleInput, onValueChange = { titleInput = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = directorInput, onValueChange = { directorInput = it }, label = { Text("Diretor") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = yearInput, onValueChange = { yearInput = it }, label = { Text("Ano") }, modifier = Modifier.fillMaxWidth())

            Button(
                onClick = {
                    if (titleInput.isNotBlank() && directorInput.isNotBlank()) {
                        filmList.add(Films(title = titleInput, director = directorInput, year = yearInput))
                        // Limpa os campos
                        titleInput = ""; directorInput = ""; yearInput = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Adicionar Filme")
            }
        }
    }
}
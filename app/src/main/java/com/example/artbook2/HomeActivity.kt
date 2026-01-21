package com.example.artbook2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.artbook2.ui.theme.ArtBook2Theme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtBook2Theme {
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: ArtViewModel){

    var isOpen by remember { mutableStateOf(false) }

    val artListState = viewModel.artlist.collectAsState(initial = emptyList())
    val artList = artListState.value


    Scaffold(
        topBar = {
            TopAppBar(title = {},
                actions = {
                    IconButton(
                        onClick = {isOpen = true}
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu Icon"
                        )
                    }
                    DropdownMenu(
                        expanded = isOpen,
                        onDismissRequest = { isOpen = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {isOpen = true
                                navController.navigate("detail")},
                            text = { Text("Add")},
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Add Icon"
                                )
                            }
                        )
                    }
                }
            )
        }
    ) {innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(innerPadding),
            columns = GridCells.Fixed(1)
        ) {
            items(artList){artItem->
                if (artList.isEmpty()){
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Data Found.",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Black
                        )
                    }
                }else{
                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .height(400.dp)
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            AsyncImage(
                                model = artItem.imageUri,
                                contentDescription = "Art Image",
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                contentScale = ContentScale.Crop
                            )

                            Column( modifier = Modifier
                                .fillMaxWidth(),
                                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Art Name: ${artItem.artName}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Artist: ${artItem.artistName}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "Yıl: ${artItem.year}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}













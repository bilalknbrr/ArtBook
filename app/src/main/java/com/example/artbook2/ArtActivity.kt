package com.example.artbook2

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.artbook2.ui.theme.ArtBook2Theme
import kotlinx.coroutines.launch

class ArtActivity : ComponentActivity() {
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
fun ArtScreen(navController: NavController, viewModel: ArtViewModel){

    val context = LocalContext.current

    var artistName = remember { mutableStateOf("") }
    var artName = remember { mutableStateOf("") }
    var year = remember { mutableStateOf("") }
    var tempSelectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> tempSelectedImageUri = uri}
    )

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if (isGranted){
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }else{
            Toast.makeText(context, "You should allow permission.", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("")},
                actions = {
                    IconButton(
                        onClick = {navController.navigate("home")}
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Back Icon"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {innerPadding ->
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            val onClickAction = {
                val permission = if (android.os.Build.VERSION.SDK_INT >= 33) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED
                    ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            context as Activity,
                            Manifest.permission.READ_MEDIA_IMAGES
                        )
                    ) {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Permission needed for gallery",
                                actionLabel = "Allow",
                                duration = SnackbarDuration.Indefinite
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                permissionLauncher.launch(permission)
                            }
                        }
                    } else {
                        permissionLauncher.launch(permission)
                    }
                }else {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (tempSelectedImageUri == null){
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Select Image",
                        modifier = Modifier
                            .size(300.dp)
                            .clickable { onClickAction() }
                    )
                }else{
                    AsyncImage(
                        model = tempSelectedImageUri,
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clickable { onClickAction() },
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = artistName.value,
                    onValueChange = { artistName.value = it },
                    label = { Text("Artist Name") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = artName.value,
                    onValueChange = {artName.value = it },
                    label = { Text("Art Name") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = year.value,
                    onValueChange = { year.value = it },
                    label = { Text("Year") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick ={
                        if (tempSelectedImageUri != null){
                            val newArt = ArtModel(
                                imageUri = tempSelectedImageUri!!,
                                artistName = artistName.value,
                                artName = artName.value,
                                year = year.value
                            )
                            viewModel.addArt(newArt)
                            navController.popBackStack()
                        }
                    },
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Save")
                }
            }
        }
    }
}



























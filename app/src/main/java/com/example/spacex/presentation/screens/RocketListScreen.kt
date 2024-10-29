package com.example.spacex.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.spacex.viewmodel.RocketViewModel
import com.example.spacex.data.model.Rocket

@Composable
fun RocketListScreen(viewModel: RocketViewModel = viewModel()) {
    val rockets by viewModel.rockets.collectAsState()
    Box(modifier = Modifier.fillMaxSize().background(Color(40,40,40)).padding(16.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(6.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if(rockets.isNotEmpty()){
                    items(rockets) { rocket -> RocketItem(rocket) }

                }else{
                    item {
                        NotFoundItem("Rockets Not Found")
                    }
                }
            }
        }
    }
}

@Composable
fun RocketItem(rocket: Rocket) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(60, 60, 60), // Background color of the card
            contentColor = Color.LightGray // Text color
        ),
        shape = RoundedCornerShape(12.dp),  // Rounded corners for the card
        elevation = CardDefaults.elevatedCardElevation(16.dp),  // Elevation for shadow effect
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Padding around the card
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Image taking up the top half of the card

            if (rocket.flickrImages.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                ) {
                    val painter = // You can add additional settings here if needed
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = rocket.flickrImages[0]).apply(block = fun ImageRequest.Builder.() {
                                    // You can add additional settings here if needed
                                }).build()
                        )
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop // Scale the image to fill the box
                    )
                }
            }

            // Details taking up the bottom half of the card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Padding inside the card for text
            ) {
                Text(
                    text = rocket.name,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Height: ${rocket.height.meters} meters",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = rocket.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun NotFoundItem(message: String){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(60, 60, 60), // Background color of the card
            contentColor = Color.LightGray // Text color
        ),
        shape = RoundedCornerShape(8.dp),  // Rounded corners for the card
        elevation = CardDefaults.elevatedCardElevation(16.dp),  // Elevation for shadow effect
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Padding around the card
    ) {
        Text(message,modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally))
    }
}






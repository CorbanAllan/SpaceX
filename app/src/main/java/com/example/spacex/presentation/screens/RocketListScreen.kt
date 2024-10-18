package com.example.spacex.presentation.screens

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
import com.example.spacex.CoilImage
import com.example.spacex.viewmodel.RocketViewModel
import com.example.spacex.data.model.Rocket

@Composable
fun RocketListScreen(viewModel: RocketViewModel = viewModel()) {
    val rockets by viewModel.rockets.collectAsState()
    Box(modifier = Modifier.fillMaxSize().background(Color(40,40,40)).padding(16.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(rockets) { rocket ->
                    RocketItem(rocket)
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
        shape = RoundedCornerShape(8.dp),  // Rounded corners for the card
        elevation = CardDefaults.elevatedCardElevation(16.dp),  // Elevation for shadow effect
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Padding around the card
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Image taking up the top half of the card
            if (rocket.flickrImages.isNotEmpty()) {
                CoilImage(
                    url = rocket.flickrImages[0],
                    modifier = Modifier
                        .fillMaxWidth() // Image fills the width of the card
                        .height(150.dp) // Height for the image
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Rounded top corners
                )
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



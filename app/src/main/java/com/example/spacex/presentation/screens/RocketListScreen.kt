package com.example.spacex.presentation.screens
import android.util.Log
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.sp
import com.example.spacex.data.model.LauncherConfig
import com.example.spacex.data.model.RocketResponse
import com.example.spacex.loadJsonFromAssets
import com.example.spacex.presentation.components.LoadingIndicator
import com.example.spacex.viewmodel.RocketsViewModel
import com.google.gson.Gson

@Composable
fun RocketListScreen(
    viewModel: RocketsViewModel = viewModel(),
    useTestData: Boolean = false // Set this to true for mock data
) {
    val context = LocalContext.current

    // Load test data if requested
    val testRockets = remember(useTestData) {
        if (useTestData) {
            val json = loadJsonFromAssets(context, "mock_rockets.json")
            val gson = Gson()
            try {
                val rocketResponse = gson.fromJson(json, RocketResponse::class.java)
                rocketResponse?.results ?: emptyList()
            } catch (e: Exception) {
                Log.e("RocketParsing", "Failed to parse JSON", e)
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    // Collect real data from ViewModel
    val rockets by viewModel.rockets.collectAsState()
    val rocketListToDisplay = if (useTestData) testRockets else rockets

    if (rocketListToDisplay.isEmpty()) {
        NotFoundItem("No rockets found")
        return
    }

    if(rockets.isEmpty()){
        LoadingIndicator()
    }else {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(40, 40, 40))
                .padding(4.dp)
        ) {
            items(rocketListToDisplay) { rocket ->
                RocketItem(rocket)
                HorizontalDivider(
                    color = Color(70, 70, 70),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun RocketItem(rocket: LauncherConfig) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .background(Color(40, 40, 40))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = rocket.name + " " + rocket.variant,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }

            rocket.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    maxLines = 3,
                    overflow = Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LandingChip(
                    "Successful Launches: " + "${rocket.successfulLaunches ?: 0}",
                    Color(70, 70, 70)
                )
                Spacer(modifier = Modifier.width(8.dp))
                LandingChip(
                    "Failed Launches: " + "${rocket.failedLaunches ?: 0}",
                    Color(70, 70, 70)
                )
            }
        }
    }
}

@Composable
fun LandingChip(text: String, backgroundColor: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(width = 180.dp, height = 24.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = Bold,
            maxLines = 1,
            overflow = Ellipsis
        )
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
            .padding(32.dp) // Padding around the card
    ) {
        Text(message,modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally))
    }
}






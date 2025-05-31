package com.example.spacex.presentation.screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.spacex.CoilImage
import com.example.spacex.data.model.Launch
import com.example.spacex.presentation.components.LoadingIndicator
import com.example.spacex.viewmodel.LaunchViewModel

@Composable
fun LaunchesScreen() {
    val viewModel = remember { LaunchViewModel() }
    val launches by viewModel.launches.collectAsState()
    var isUpcoming by remember { mutableStateOf(true) }
    val isLoading = launches.isEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(40, 40, 40))
            .padding(16.dp)
    ) {
        // Toggle Buttons Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(40, 40, 40))
                .border(
                    width = 1.dp,
                    color = Color.Gray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Button(
                onClick = { isUpcoming = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(40, 40, 40)),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                elevation = null,
            ) {
                Text("Upcoming", color = Color.White)
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.Gray.copy(alpha = 0.3f))
            )

            Button(
                onClick = { isUpcoming = false },
                colors = ButtonDefaults.buttonColors(containerColor = Color(40, 40, 40)),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                elevation = null,
            ) {
                Text("Past", color = Color.White)
            }
        }

    if (isLoading) {
        LoadingIndicator()
    }else {
        // Load data based on selection
        LaunchedEffect(isUpcoming) {
            viewModel.fetchLaunches(isUpcoming)
        }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                if (launches.isNotEmpty()) {
                    items(launches) { launch -> LaunchItem(launch) }
                } else {
                    item {
                        NotFoundItem("Launches Not Found")
                    }
                }
            }
        }
    }
}


@Composable
fun LaunchItem(launch: Launch) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(60, 60, 60)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = launch.name.take(21) + if (launch.name.length > 21) "..." else "",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Text(
                        text = launch.dateUtc.take(10),
                        color = Color.LightGray
                    )
                }

                launch.image?.let { imageUrl ->
                    CoilImage(
                        url = imageUrl,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Status: ${launch.status.name}",
                color = Color.White
            )

            launch.mission?.description?.let {
                Text(
                    text = it.take(100) + if (it.length > 100) "..." else "",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}



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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.TextButton
import androidx.compose.runtime.snapshotFlow

@Composable
fun LaunchesScreen(viewModel: LaunchViewModel = viewModel()) {
    val launches by viewModel.launches.collectAsState()
    val listState = rememberLazyListState()
    val isLoading = launches.isEmpty()
    var selectedTab by remember { mutableStateOf(true) } // true = Upcoming, false = Past

    // Scroll detection for pagination
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { index ->
                if (index == launches.lastIndex) {
                    viewModel.fetchMoreLaunches()
                }
            }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(40, 40, 40))
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(40, 40, 40))
                .border(BorderStroke(1.dp, Color.Gray)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    selectedTab = true
                    viewModel.fetchInitialLaunches(true)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Upcoming", color = if (selectedTab) Color.White else Color.Gray)
            }
            Box(
                Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.Gray)
            )
            TextButton(
                onClick = {
                    selectedTab = false
                    viewModel.fetchInitialLaunches(false)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Past", color = if (!selectedTab) Color.White else Color.Gray)
            }
        }

        if (isLoading) {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(state = listState) {
                items(launches) { launch ->
                    LaunchItem(launch)
                }
                item {
                    Spacer(modifier = Modifier.height(60.dp)) // bottom padding
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



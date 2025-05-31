package com.example.spacex.presentation.screens
import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.spacex.data.model.Article
import com.example.spacex.viewmodel.NewsViewModel
import androidx.core.net.toUri
import com.example.spacex.data.model.UpcomingLaunch
import com.example.spacex.viewmodel.UpcomingLaunchViewModel
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(viewModel: NewsViewModel = viewModel(), upcomingLaunchViewModel: UpcomingLaunchViewModel = viewModel()) {
    val launches by upcomingLaunchViewModel.launches.collectAsState()
    val articles by viewModel.newsArticles.collectAsState()
    Box(modifier = Modifier.fillMaxSize().background(Color(40, 40, 40)).padding(16.dp)) {
        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Latest News",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (articles.isNotEmpty()) {
                    items(articles) { article ->
                        NewsItem(article)
                    }
                } else {
                    item {
                        NotFoundItem("No News Found")
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Upcoming Launch",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            UpcomingLaunchItem(launches)
        }
    }
}

@Composable
fun NewsItem(article: Article) {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(60, 60, 60),
            contentColor = Color.LightGray
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(16.dp),
        modifier = Modifier
            .width(300.dp)
            .height(280.dp)
            .padding(8.dp)
            .clickable {
                // Intent to open URL in browser
                val intent = Intent(Intent.ACTION_VIEW, article.url.toUri())
                context.startActivity(intent)
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Image as the background
            article.imageUrl?.let { imageUrl ->
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Text overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 100f
                        )
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                article.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.LightGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun UpcomingLaunchItem(upcomingLaunches: List<UpcomingLaunch>) {
    if (upcomingLaunches.isNotEmpty()) {
        val launch = upcomingLaunches.first()

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(60, 60, 60),
                contentColor = Color.LightGray
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.elevatedCardElevation(16.dp),
            modifier = Modifier
                .width(400.dp)
                .height(320.dp)
                .padding(8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Background image
                Image(
                    painter = launch.image?.let { imageUrl ->
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .build()
                        )
                    } ?: rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data("https://via.placeholder.com/300x200")
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)), // this is optional here
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End) {
                    LaunchCountdown(launch.net)
                }

                // Text overlay INSIDE the same Box
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = 100f
                            )
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = launch.rocket.configuration.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = launch.pad.location.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.LightGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    } else {
        NotFoundItem("No Launch Found")
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun LaunchCountdown(netTime: String) {
    val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME // "2024-06-12T14:00:00Z"
    val launchTime = remember(netTime) {
        try {
            ZonedDateTime.parse(netTime, formatter)
        } catch (e: Exception) {
            null
        }
    }

    var countdownText by remember { mutableStateOf("Calculating...") }

    LaunchedEffect(launchTime) {
        while (launchTime != null) {
            val now = ZonedDateTime.now(ZoneOffset.UTC)
            val duration = Duration.between(now, launchTime)

            if (duration.isNegative) {
                countdownText = "Launched!"
                break
            } else {
                val days = duration.toDays()
                val hours = duration.toHours() % 24
                val minutes = duration.toMinutes() % 60
                val seconds = duration.seconds % 60

                countdownText = buildString {
                    if (days > 0) append("${days}d ")
                    append(String.format("%02d:%02d:%02d", hours, minutes, seconds))
                }
            }

            delay(1000L)
        }
    }

    Text(
        text = countdownText,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}
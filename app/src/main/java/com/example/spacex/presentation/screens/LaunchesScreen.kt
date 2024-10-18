package com.example.spacex.presentation.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.spacex.CoilImage
import com.example.spacex.R
import com.example.spacex.data.model.Launch
import com.example.spacex.viewmodel.LaunchViewModel

@Composable
fun LaunchesScreen(viewModel: LaunchViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize().background(Color(40,40,40)).padding(16.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(6.dp))
            LazyColumn {
                items(viewModel.launches) { launch ->
                    LaunchItem(launch)
                }
            }
        }
    }

}

@Composable
fun LaunchItem(launch: Launch) {
    Card(
        colors = CardColors(Color(60,60,60),Color.LightGray,Color(60,60,60),Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(16.dp) ,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)// Padding around the card
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column{
                    Text(text = launch.name.take(21) + if (launch.name.length > 21) "..." else "", style = MaterialTheme.typography.titleLarge)
                }
                if(launch.links.patch.small != null){
                    launch.links.patch.small.let {
                        CoilImage(
                            url = it,
                            modifier = Modifier.size(40.dp) // Adjust size as needed
                        )
                    }
                }

            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = launch.date_utc.take(10))
            Text(text = "Status: " + if (launch.success == true) "Success" else "Failed")
        }
    }
}


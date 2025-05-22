package com.example.places.hitasofttest.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.places.hitasofttest.AddViewModel
import com.example.places.hitasofttest.R
import com.example.places.hitasofttest.navigation.AllDestinations.navController
import com.example.places.hitasofttest.navigation.Route


@Preview
@Composable
fun AddScreen(viewModel: AddViewModel = hiltViewModel()) {
    BackHandler(enabled = true) {
        navController.popBackStack()
        navController.navigate(Route.Dashboard.route)
    }

    LaunchedEffect(true) {
        viewModel.addNextVideo()
    }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF1A0033), // Purple-dark background
        tonalElevation = 4.dp
    ) {
        Column {
            CustomTopBarAdd()
            FourItemsLazyRow()
            CommentSectionDemo()
        }
    }
}




@Composable
fun CustomTopBarAdd(viewModel: AddViewModel = hiltViewModel()) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFF1A0033))
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .padding(top = 40.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // User Avatar + Username + Likes + Plus Button
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // replace with real image
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Column {
                    Text(text = "اسمك اللهم ت...", color = Color.White, fontSize = 12.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "30.4K", color = Color.White, fontSize = 10.sp)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color(0xFF9B4DFF), CircleShape).clickable {
                            viewModel.addNextVideo()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }

            // Right side icons (avatars + number circle + close)
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(3) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground), // replace
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.DarkGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "12", color = Color.White, fontSize = 10.sp)
                }

                Spacer(modifier = Modifier.width(6.dp))

                Icon(Icons.Default.Close, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Badges and Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Badge(text = "Top 6", icon = Icons.Default.Add)
                Spacer(modifier = Modifier.width(6.dp))
                Badge(text = "Hourly Ranking", icon = Icons.Default.Favorite)
            }

            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9B4DFF)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("دندنة هايبو", color = Color.White)
            }
        }
    }
}

@Composable
fun FourItemsLazyRow(viewModel: AddViewModel = hiltViewModel()) {

    val displayedVideoIds = viewModel.displayedVideoIds


    LazyVerticalGrid(
        columns = GridCells.Fixed(4), // 2 columns per row
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp), // height as needed
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(displayedVideoIds.size) { index ->
            VideoCard(displayedVideoIds.get(index))
        }
    }
}







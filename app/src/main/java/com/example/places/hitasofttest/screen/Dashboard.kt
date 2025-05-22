package com.example.places.hitasofttest.screen

import android.view.ViewGroup
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.example.places.hitasofttest.R
import com.example.places.hitasofttest.data.Comment
import com.example.places.hitasofttest.navigation.AllDestinations.navController
import com.example.places.hitasofttest.navigation.Route
import com.example.places.hitasofttest.utils.CommonMethods.isInternetAvailable
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Preview
@Composable
fun Dashboard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF1A0033), // Purple-dark background
        tonalElevation = 4.dp
    ) {
        Column {
            CustomTopBar()
            TwoItemsLazyRow()
            CommentSectionDemo()
        }
    }
}

@Composable
fun TwoItemsLazyRow() {

    val videoIds = listOf(
        "6-N8cH46OEY", // Shorts - tech quick tip
        "dQw4w9WgXcQ", // Rick Astley - Never Gonna Give You Up
        "E7wJTI-1dvQ", // Pixar short
        "M7FIvfx5J10", // LG elevator prank
        "lgarf-rSgC0", // Cute cat video
        "sNPnbI1arSE", // Creepy trailer
        "fLexgOxsZu0", // Dance monkey performance
        "FTQbiNvZqaY", // Daft Punk - Harder Better Faster
        "3tmd-ClpJxA", // Charlie Puth - Attention
        "fRh_vgS2dFE", // Justin Bieber - Sorry
        "09R8_2nJtjg", // Maroon 5 - Sugar
        "M4ZoCHID9GI", // Amazing street performance
        "iik25wqIuFo", // Motivational speech
        "oHg5SJYRHA0", // Fun meme
        "RubxnoTeL5Y", // 10 Hours of relaxing nature sounds
        "l482T0yNkeo", // Canon Rock Guitar Cover
        "9bZkp7q19f0", // Gangnam Style
        "C0DPdy98e4c", // GoPro Fireman
        "K4TOrB7at0Y", // Imagine Dragons - Thunder
        "JGwWNGJdvx8"  // Ed Sheeran - Shape of You
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 columns per row
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp), // height as needed
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(videoIds.size) { index ->
            VideoCard(videoIds.get(index))
        }
    }
}

@Composable
fun VideoCard(url: String) {
    val mContext = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), // square cards
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        if (isInternetAvailable(context = mContext)) {
            YouTubePlayerComposable(videoId = url)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("No internet connection", color = Color.White)
            }
        }
    }
}

@Composable
fun YouTubePlayerComposable(videoId: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = rememberUpdatedState(LocalContext.current as LifecycleOwner)
    var isVideoReady by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        AndroidView(
            factory = { ctx ->
                YouTubePlayerView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    lifecycleOwner.value.lifecycle.addObserver(this)

                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(videoId, 0f)
                            isVideoReady = true
                        }
                    })
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (!isVideoReady) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}




@Composable
fun CustomTopBar() {
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
                            navController.popBackStack()
                            navController.navigate(Route.AddScreen.route)
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
fun Badge(text: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xFF2C2C2C), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, color = Color.White, fontSize = 10.sp)
    }
}

@Composable
fun CommentSectionDemo() {
    var comments by remember { mutableStateOf(
        listOf(
            Comment("Alice", "This is a great post!", "2 hours ago"),
            Comment("Bob", "Thanks for sharing.", "1 hour ago")
        )
    )}

    CommentSection(
        comments = comments,
        onSendComment = { newComment ->
            comments = comments + Comment("You", newComment, "Just now")
        }
    )
}



@Composable
fun CommentSection(
    comments: List<Comment>,
    onSendComment: (String) -> Unit
) {
    var newCommentText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Comment list
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(comments.size) { comment ->
                CommentItem(comments.get(comment))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Emoji icon before TextField
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { /* Open image picker */ }) {
                    Icon(
                        imageVector = Icons.Default.EmojiEmotions,
                        contentDescription = "Image",
                        tint = Color.White
                    )
                }
            }

            // "Say Hi" TextField
            TextField(
                value = newCommentText,
                onValueChange = { newCommentText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Say Hi...") },
                maxLines = 4,
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (newCommentText.isNotBlank()) {
                            onSendComment(newCommentText.trim())
                            newCommentText = ""
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Emoji Button

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { /* Open image picker */ }) {
                    Icon(
                        imageVector = Icons.Default.EmojiEmotions,
                        contentDescription = "Image",
                        tint = Color.White
                    )
                }
                Text("Image", fontSize = 10.sp,color = Color.White)
            }


            // Image Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { /* Open image picker */ }) {
                    Icon(
                        imageVector = Icons.Default.CardGiftcard,
                        contentDescription = "Image",
                        tint = Color.White
                    )
                }
                Text("Gift", fontSize = 10.sp, color = Color.White)
            }


            // Attach Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { /* Open file chooser */ }) {
                    Icon(imageVector = Icons.Default.AttachFile, contentDescription = "Attach", tint = Color.White)
                }
                Text("Attach", fontSize = 10.sp,color = Color.White)
            }

            // Send Button
            IconButton(
                onClick = {
                    if (newCommentText.isNotBlank()) {
                        onSendComment(newCommentText.trim())
                        newCommentText = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Avatar circle with initials
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // replace
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    comment.userName,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(
                    comment.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                comment.text,
                color = Color.Green,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}





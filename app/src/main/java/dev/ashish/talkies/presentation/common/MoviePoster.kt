package dev.ashish.talkies.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dev.ashish.talkies.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MoviePoster (
    modifier: Modifier = Modifier,
    height: Dp = 120.dp,
    width: Dp = 180.dp,
    image: String
){

    val painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w185${image}") {
        crossfade(durationMillis = 200)
        error(R.drawable.ic_placeholder)
        placeholder(R.drawable.ic_placeholder)
    }

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "poster",
            contentScale = ContentScale.Crop
        )
    }
}
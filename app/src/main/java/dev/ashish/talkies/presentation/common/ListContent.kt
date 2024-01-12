package dev.ashish.talkies.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dev.ashish.talkies.R
import dev.ashish.talkies.models.Movie

@Composable
fun ListContent(
    items: LazyPagingItems<Movie>,
    navHostController: NavHostController
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp),
        columns = GridCells.Adaptive(180.dp)
    ){
        items(count = items.itemCount){
            items[it]?.let{image ->
                MovieItem(image){
                    navHostController.navigate("Details/"+image.id)
                }
            }
        }
    }
}
@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieItem(movie:Movie,onItemClick: () -> Unit)
{
    val painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w185${movie.poster_path}") {
        crossfade(durationMillis = 200)
        error(R.drawable.ic_placeholder)
        placeholder(R.drawable.ic_placeholder)
    }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .clickable {
                onItemClick()
            }
            .height(300.dp)
            .padding(5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    onItemClick()
                }
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop
            )
            Surface(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.6f)
            ) {}
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = movie.title,
                    color = Color.White,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                ReleaseYear(
                    modifier = Modifier.weight(1f),
                    year = movie.release_date
                )
            }
        }
    }
}
@Composable
fun ReleaseYear(
    modifier: Modifier,
    year: String
) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Text(
            text = year,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
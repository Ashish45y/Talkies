package dev.ashish.talkies.presentation.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import dev.ashish.talkies.R
import dev.ashish.talkies.models.Movie
import dev.ashish.talkies.models.MovieDetails
import dev.ashish.talkies.presentation.common.CustomImageButton
import dev.ashish.talkies.presentation.common.MoviePoster
import dev.ashish.talkies.presentation.common.RatingBar

@OptIn(ExperimentalPagingApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun DetailsScreen(
    navHostController: NavHostController,
    movieId: Int,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {

    val movieDetails by detailsViewModel.movieDetails.observeAsState(null)

    val recomendations = detailsViewModel.movie_recommendations.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit){
        detailsViewModel.getMovieDetails(movieId)
        detailsViewModel.getRecommendations(movieId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CollapsingEffectScreen(navHostController, movieDetails, recomendations)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingEffectScreen(
    navHostController: NavHostController,
    movieDetails: MovieDetails?,
    recommendations: LazyPagingItems<Movie>
) {

    //TODO replace the link wih the api fetched link
    val painter =
        rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movieDetails?.backdrop_path}") {
            crossfade(durationMillis = 200)
            error(R.drawable.ic_placeholder)
            placeholder(R.drawable.ic_placeholder)
        }

    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    LazyColumn(
        Modifier.fillMaxSize(),
        lazyListState,
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.5f
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
                    .height(240.dp)
            ) {

                Image(
                    modifier = Modifier
                        .height(240.dp)
                        .fillMaxSize(),
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Card(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    onClick = {
                        navHostController.popBackStack()
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back Button",
                        tint = Color.DarkGray
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    if (isSystemInDarkTheme()) Color(0xFF181716) else Color.White
                                ),
                                startY = 300f
                            )
                        )
                )
            }

        }
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if(movieDetails!=null){
                    MovieDetails(movieDetails, recommendations)
                }else{
                    Log.d("DetailsScreen", "movie details null")
                }
            }
        }
    }
}

@Composable
fun MovieDetails(
    movieDetails: MovieDetails?,
    recommendations: LazyPagingItems<Movie>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                MoviePoster(image = movieDetails?.poster_path!!)

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = buildAnnotatedString {
                            append(movieDetails.relese_year)
                            append(" - ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append(movieDetails.genresData)
                            }
                        },
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movieDetails.title!!,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    RatingBar(
                        modifier = Modifier
                            .size(20.dp),
                        rating = (movieDetails.vote_average!!/2.0),
                        onRatingChange = {}
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = buildAnnotatedString {
                            append(movieDetails.overview)
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append("More")
                            }
                        },
                        textAlign = TextAlign.Justify,
                        fontSize = 12.sp,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append("Production Countries: ")
                        }
                        append(movieDetails?.prodCountries)

                    },
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append("Production Company: ")
                        }
                        append(movieDetails?.prodCompany)

                    },
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append("Runtime : ")
                        }
                        append(movieDetails?.runTime)
                    },
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    CustomImageButton(
                        modifier = Modifier
                            .weight(1f),
                        icon = Icons.Rounded.Add,
                        text = "My List",
                        onClick = {}
                    )
                    CustomImageButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Rounded.ThumbUp,
                        text = "Rate",
                        onClick = {}
                    )
                    CustomImageButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Rounded.Share,
                        text = "Share",
                        onClick = {}
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    text = "Recommendations",
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 5.dp)
                ) {

                    items(recommendations.itemCount) {
                        recommendations[it]?.let { image ->
                            MoviePoster(image = image.poster_path)
                        }
                    }
                }

            }

        }

    }

}
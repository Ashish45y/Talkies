package dev.ashish.talkies.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ashish.talkies.data.repository.MovieRepository
import dev.ashish.talkies.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    val movieDetails = repository.movieDetails

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId)
        }
    }

    private val _movie_recommendations = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movie_recommendations = _movie_recommendations

    fun getRecommendations(movieId: Int) {
        viewModelScope.launch {
            repository.getRecommendations(movieId = movieId).cachedIn(viewModelScope).collect {
                _movie_recommendations.value = it
            }
        }
    }

}
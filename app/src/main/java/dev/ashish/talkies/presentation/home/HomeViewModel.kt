package dev.ashish.talkies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ashish.talkies.data.repository.MovieRepository
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: MovieRepository
): ViewModel() {

    val getAllImages = repository.getAllMovies().cachedIn(viewModelScope)

}
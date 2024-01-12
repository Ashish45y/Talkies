package dev.ashish.talkies.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.ashish.talkies.data.local.database.MovieDatabase
import dev.ashish.talkies.data.paging.MovieRemoteMediator
import dev.ashish.talkies.data.paging.RecommendationPagingSource
import dev.ashish.talkies.data.paging.SearchPagingSource
import dev.ashish.talkies.data.remote.MovieApi
import dev.ashish.talkies.models.Movie
import dev.ashish.talkies.models.MovieDetails
import dev.ashish.talkies.models.MovieRemoteKeys
import dev.ashish.talkies.utils.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val movieAPI: MovieApi,
    private val movieDatabase: MovieDatabase
) {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("MySharedPref",
        Context.MODE_PRIVATE
    )

    fun getAllMovies(): Flow<PagingData<Movie>> {
        Log.d("MovieRepository", "repository_hit")
        val pagingSourceFactory = { movieDatabase.movieDao().getAllImages() }
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                prefetchDistance = 2,
                initialLoadSize = 19,
                enablePlaceholders = true
            ),
            remoteMediator = MovieRemoteMediator(
                movieApi = movieAPI,
                movieDatabase = movieDatabase,
                sharedPreferences = sharedPreferences
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(movieApi = movieAPI, query = query)
            }
        ).flow
    }

    fun getRecommendations(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                RecommendationPagingSource(movieApi = movieAPI, movieId = movieId)
            }
        ).flow
    }

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> get() = _movieDetails

    suspend fun getMovieDetails(movieId: Int){
        Log.d("MovieRepository", movieId.toString())
        try{
            val response = movieAPI.getDetails(movieId)
            _movieDetails.postValue(response)
        }catch (e:Exception){
            Log.d("MovieRepository", e.message.toString())
        }
    }


    suspend fun workerTaskInBackGround(){

        val currentPage = sharedPreferences.getInt("currentPage", 1)
        Log.d("currentPage1", currentPage.toString())
        val response = movieAPI.getAllMovies(page = currentPage + 1, language = "en-US")

        val endOfPaginationReached = currentPage == 501

        val prevPage = if (currentPage == 1) null else currentPage - 1
        val nextPage = if (endOfPaginationReached) null else currentPage + 1

        sharedPreferences.edit().putInt("currentPage", nextPage!!).apply()

        movieDatabase.withTransaction {

            val keys = response.results.map { movie ->
                MovieRemoteKeys(
                    movieId = movie.moveId,
                    prevPage = prevPage,
                    nextPage = nextPage
                )
            }
            //store in the database or save
            movieDatabase.movieDao().addMovies(movies = response.results)
            movieDatabase.movieRemoteKeysDao().addAllRemoteKeys(remoteKeys = keys)
        }
    }
}
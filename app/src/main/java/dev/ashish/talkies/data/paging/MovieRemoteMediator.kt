package dev.ashish.talkies.data.paging

import android.content.SharedPreferences
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.ashish.talkies.data.local.database.MovieDatabase
import dev.ashish.talkies.data.remote.MovieApi
import dev.ashish.talkies.models.Movie
import dev.ashish.talkies.models.MovieRemoteKeys

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase,
    private val sharedPreferences: SharedPreferences
): RemoteMediator<Int, Movie>() {

    private val movieDao = movieDatabase.movieDao()
    private val movieRemoteKeysDao = movieDatabase.movieRemoteKeysDao()

    /*
        here we will calculate the current page which we are loading from api as well as the next and prev page
        and based on the page values we can tell the remote mediator which page to request next whenever we want to
        load more data in our application
    */
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = movieApi.getAllMovies(page = currentPage)

            val endOfPaginationReached = currentPage == 501

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            sharedPreferences.edit().putInt("currentPage", nextPage!!).apply()

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //invalidate the data
                    Log.d("MovieRemoteMediator", "Invalidate")
                    movieDao.deleteAllMovies()
                    movieRemoteKeysDao.deleteAllRemoteKeys()
                }

                val keys = response.results.map { movie ->
                    MovieRemoteKeys(
                        movieId = movie.moveId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                Log.d("MovieRemoteMediator", keys.size.toString())

                movieDao.addMovies(movies = response.results)
                movieRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)

            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): MovieRemoteKeys? {
        Log.d("MovieRemoteMediator", "Refresh")
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Movie>
    ): MovieRemoteKeys? {
        Log.d("MovieRemoteMediator", "Prepend")
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getRemoteKeys(id = movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Movie>
    ): MovieRemoteKeys? {
        Log.d("MovieRemoteMediator", "Append")
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getRemoteKeys(id = movie.moveId)
            }
    }

}
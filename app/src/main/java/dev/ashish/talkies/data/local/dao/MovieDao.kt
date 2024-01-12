package dev.ashish.talkies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ashish.talkies.models.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table")
    fun getAllImages(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<Movie>)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllMovies()

}
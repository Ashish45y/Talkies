package dev.ashish.talkies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.ashish.talkies.data.local.dao.MovieDao
import dev.ashish.talkies.data.local.dao.MovieRemoteKeyDao
import dev.ashish.talkies.models.Movie
import dev.ashish.talkies.models.MovieRemoteKeys

@Database(entities = [Movie::class, MovieRemoteKeys::class], version = 22)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun movieRemoteKeysDao(): MovieRemoteKeyDao
}
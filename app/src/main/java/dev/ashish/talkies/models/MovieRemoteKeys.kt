package dev.ashish.talkies.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.ashish.talkies.utils.Constants.MOVIE_REMOTE_KEY_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = MOVIE_REMOTE_KEY_TABLE)
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val movieId: Int,
    val prevPage: Int?,
    val nextPage: Int?
): Parcelable
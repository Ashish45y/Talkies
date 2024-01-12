package dev.ashish.talkies.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.ashish.talkies.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.MOVIE_TABLE)
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val moveId: Int,
    val id: Int,
    val title: String,
    val poster_path: String,
    val release_date: String,
): Parcelable
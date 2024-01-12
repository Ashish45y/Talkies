package dev.ashish.talkies.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(

    val results: List<Movie>

): Parcelable
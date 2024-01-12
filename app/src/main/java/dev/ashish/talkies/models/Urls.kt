package dev.ashish.talkies.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Urls(
    val regular: String
): Parcelable
package dev.ashish.talkies.models

import android.os.Build
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MovieDetails(
    val title:String?,
    val backdrop_path: String?,
    val poster_path: String?,
    val genres: List<Genres>,
    val production_countries: List<ProductionCountries>,
    val production_companies: List<ProductionCompanies>,
    val overview: String?,
    val runtime: Int?,
    val vote_average: Float?,
    val release_date: String?
) {
    val runTime: String
        get() {
            val hours = runtime!! / 60
            val minutes = runtime % 60
            return "$hours hrs $minutes min"
        }
    val relese_year: String
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val localDate = LocalDate.parse(release_date, formatter)
                return localDate.year.toString()
            } else {
                return ""
            }
        }
    val genresData: String
        get() {
            return genres.joinToString(", ") { it.name }
        }
    val prodCountries: String get() = production_countries.joinToString(", ") { it.name }

    val prodCompany: String get() = production_companies.joinToString(", ") { it.name }
}
package dev.ashish.talkies.navigation

sealed class Routes(val route: String) {

    data object Home: Routes("Home")
    data object Search: Routes("Search")
    data object Details: Routes("Details/{movieId}")
}
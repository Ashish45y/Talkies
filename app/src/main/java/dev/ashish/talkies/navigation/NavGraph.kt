package dev.ashish.talkies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import dev.ashish.talkies.presentation.details.DetailsScreen
import dev.ashish.talkies.presentation.home.HomeScreen
import dev.ashish.talkies.presentation.search.SearchScreen

@OptIn(ExperimentalPagingApi::class)
@Composable
fun NavGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navHostController, startDestination = Routes.Home.route){
        composable(Routes.Home.route){
            HomeScreen(navHostController)
        }
        composable(Routes.Search.route){
            SearchScreen(navHostController)
        }
        composable(Routes.Details.route, arguments = listOf(
            navArgument("movieId"){
                type = NavType.IntType
            }
        )){
            val movieId = it.arguments!!.getInt("movieId")
            DetailsScreen(navHostController,movieId)
        }
    }


}
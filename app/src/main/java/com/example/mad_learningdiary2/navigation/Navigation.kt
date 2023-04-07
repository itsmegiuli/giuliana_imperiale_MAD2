package com.example.mad_learningdiary2.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost //added in build.gradle
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mad_learningdiary2.screens.*


@Composable
fun MyNavigation() {
    //navcontrol instance:
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = ScreenRoutes.Home.route) { // first screen & also last screen (--> back)

        //homescreen
        composable(
            route = ScreenRoutes.Home.route) {
            HomeScreen(navController = navController)
        }

        //details
        composable(
            route= ScreenRoutes.Details.route,
            arguments= listOf(
                navArgument(name = DETAIL_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) {backStackEntry ->
        DetailScreen(navController = navController, movieId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY))
        }

        //favorites
        composable(
            route= ScreenRoutes.Favorites.route) {
            FavoritesScreen(navController = navController)
        }

        //add movie
        composable(ScreenRoutes.AddMovieScreen.route) {
            AddMovieScreen(navController = navController)
        }
        //composable("detailscreen") { DetailScreen() }
    }
}
package com.example.mad_learningdiary2.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost //added in build.gradle
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mad_learningdiary2.screens.*
import com.example.mad_learningdiary2.viewModels.MoviesViewModel


@Composable
fun MyNavigation() {
    //navcontrol instance:
    val navController = rememberNavController()
    val movieViewModel: MoviesViewModel = viewModel() //initialized here bc of "3 rules of state hoisting" (see lecture slides)

    NavHost(navController = navController,
        startDestination = ScreenRoutes.Home.route) { // first screen & also last screen (--> back)

        //homescreen
        composable(
            route = ScreenRoutes.Home.route) {
            HomeScreen(
                navController = navController,
                movieViewModel = movieViewModel //added new parameter to use funcs of movieViewModel in each screen
                )
        }

        //details
        composable(
            route= ScreenRoutes.Details.route,
            arguments= listOf(
                navArgument(name = DETAIL_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) {backStackEntry ->
        DetailScreen(
            navController = navController,
            movieId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
            movieViewModel = movieViewModel)
        }

        //favorites
        composable(
            route= ScreenRoutes.Favorites.route) {
            FavoritesScreen(
                navController = navController,
                movieViewModel = movieViewModel)
        }

        //add movie
        composable(ScreenRoutes.AddMovieScreen.route) {
            AddMovieScreen(navController = navController)
        }
        //composable("detailscreen") { DetailScreen() }
    }
}
package com.example.mad_learningdiary2.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost //added in build.gradle
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mad_learningdiary2.ScreenRoutes
import com.example.mad_learningdiary2.screens.DetailScreen
import com.example.mad_learningdiary2.screens.FavoritesScreen
import com.example.mad_learningdiary2.screens.HomeScreen
import com.example.mad_learningdiary2.screens.MyList


@Composable
fun MyNavigation() {
    //navcontrol instance:
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = ScreenRoutes.Home.route) { // first screen & also last screen (--> back)
        composable(route = ScreenRoutes.Home.route) { HomeScreen(navController = navController) }
        composable(
            route= ScreenRoutes.Details.route + "/{movieId}", //if more than 1 argument: separate it with /
            arguments= listOf(
                navArgument("movieId") {
                type = NavType.StringType
            })
        ) {backStackEntry ->
        DetailScreen(navController = navController, movieId = backStackEntry.arguments?.getString("movieId"))
        }
        composable(route=ScreenRoutes.Favorites.route) { FavoritesScreen(navController = navController)}

        //composable("detailscreen") { DetailScreen() }
    }
}
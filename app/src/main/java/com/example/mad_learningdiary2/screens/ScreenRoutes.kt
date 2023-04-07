package com.example.mad_learningdiary2.screens

const val DETAIL_ARGUMENT_KEY = "movieId"


sealed class ScreenRoutes(val route: String){
    object Home: ScreenRoutes(route = "homescreen")

   // object Details: ScreenRoutes(route = "detailscreen")

    //LD4: taken from leon's prep code:
    object Details: ScreenRoutes(route= "detailscreen/{$DETAIL_ARGUMENT_KEY}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }

    object Favorites: ScreenRoutes(route="favoritesscreen")

    object AddMovieScreen: ScreenRoutes(route="addmoviescreen")
}


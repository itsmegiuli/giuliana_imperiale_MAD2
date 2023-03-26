package com.example.mad_learningdiary2

sealed class ScreenRoutes(val route: String){
    object Home: ScreenRoutes(route = "homescreen")
    object Details: ScreenRoutes(route = "detailscreen")
}


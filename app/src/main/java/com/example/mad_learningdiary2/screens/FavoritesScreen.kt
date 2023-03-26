package com.example.mad_learningdiary2.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies
import com.example.mad_learningdiary2.widgets.SimpleAppBar

@Composable
fun FavoritesScreen(navController: NavController) {
    val tempList: List<Movie> = listOf(
        getMovies()[7], //breaking bad
        getMovies()[8], //narcos
        getMovies()[4]  // interstellar
    )
    Column(Modifier.fillMaxWidth()) {
        SimpleAppBar(title = "Favorites", navController = navController)
        Column {
            MyList(movies = tempList, navController = navController)
        }
    }
}


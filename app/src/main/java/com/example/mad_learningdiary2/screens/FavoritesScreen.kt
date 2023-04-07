package com.example.mad_learningdiary2.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies
import com.example.mad_learningdiary2.widgets.MovieRow
import com.example.mad_learningdiary2.widgets.SimpleTopAppBar

//prep code by leon
@Composable
fun FavoritesScreen(navController: NavController){
    Scaffold(topBar = {
        SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
            Text(text = "My Favorite Movies")
        }
    }){ padding ->
        val movieList: List<Movie> = getMovies()

        Column(modifier = Modifier.padding(padding)) {
            LazyColumn {
                items(movieList){ movie ->
                    MovieRow(
                        movie = movie,
                        onItemClick = { movieId ->
                            navController.navigate(route = ScreenRoutes.Details.withId(movieId))
                        }
                    )
                }
            }
        }
    }
}
package com.example.mad_learningdiary2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies
import com.example.mad_learningdiary2.viewModels.MoviesViewModel
import com.example.mad_learningdiary2.widgets.HomeTopAppBar
import com.example.mad_learningdiary2.widgets.MovieRow

//prep code by leon
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    movieViewModel: MoviesViewModel //inject the viewModel instance to use it's functionalities, movieViewModel: com.example.mad_learningdiary2.viewModels.MoviesViewModel){}
               ){
    Scaffold(topBar = {
        HomeTopAppBar(
            title = "Home",
            menuContent = {
                DropdownMenuItem(onClick = { navController.navigate(ScreenRoutes.AddMovieScreen.route) }) {
                    Row {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Add Movie", modifier = Modifier.padding(4.dp))
                        Text(text = "Add Movie", modifier = Modifier
                            .width(100.dp)
                            .padding(4.dp))
                    }
                }
                DropdownMenuItem(onClick = { navController.navigate(ScreenRoutes.Favorites.route) }) {
                    Row {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites", modifier = Modifier.padding(4.dp))
                        Text(text = "Favorites", modifier = Modifier
                            .width(100.dp)
                            .padding(4.dp))
                    }
                }
            }
        )
    }) { padding ->
        MainContent(modifier = Modifier.padding(padding), navController = navController, movieViewModel= movieViewModel )
    }
}

@Composable
fun MainContent(
    modifier: Modifier,
    navController: NavController,
    movieViewModel: MoviesViewModel
) {
    val movies = getMovies()
    MovieList(
        modifier = modifier,
        navController = navController,
        movies = movies,
        movieViewModel = movieViewModel
    )
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    navController: NavController,
    movies: List<Movie> = getMovies(),
    movieViewModel: MoviesViewModel
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movieViewModel.movieList) { movie ->
            MovieRow(
                movie = movie,
                onItemClick = { movieId ->
                    navController.navigate(ScreenRoutes.Details.withId(movieId))
                },
                onFavClick = {movieViewModel.toggleFavoriteState(movie)}
            )
        }
    }
}



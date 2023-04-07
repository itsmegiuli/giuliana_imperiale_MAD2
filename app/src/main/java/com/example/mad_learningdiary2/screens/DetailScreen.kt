package com.example.mad_learningdiary2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies
import com.example.mad_learningdiary2.viewModels.MoviesViewModel
import com.example.mad_learningdiary2.widgets.HorizontalScrollableImageView
import com.example.mad_learningdiary2.widgets.MovieRow
import com.example.mad_learningdiary2.widgets.SimpleTopAppBar

//prep code by leon
fun filterMovie(movieId: String): Movie {
    return getMovies().filter { it.id == movieId}[0]
}
@Composable
fun DetailScreen(
    navController: NavController,
    movieId: String?,
    movieViewModel: MoviesViewModel
){

    movieId?.let {
        //val movie = filterMovie(movieId = movieId)
        val movie = movieViewModel.filterMovie(movieId = movieId)
        // needed for show/hide snackbar
        val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`

        Scaffold(scaffoldState = scaffoldState, // attaching `scaffoldState` to the `Scaffold`
            topBar = {
                SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                    Text(text = movie.title)
                }
            },
        ) { padding ->
            MainContent(Modifier.padding(padding), movie, movieViewModel)
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, movie: Movie, movieViewModel: MoviesViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            MovieRow(
                movie = movie,
                onFavClick = {movieViewModel.toggleFavoriteState(movie)})

            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Text(text = "Movie Images", style = MaterialTheme.typography.h5)

            HorizontalScrollableImageView(movie = movie)
        }
    }
}
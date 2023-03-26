package com.example.mad_learningdiary2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.example.mad_learningdiary2.widgets.SimpleAppBar


@Composable
fun DetailScreen(navController: NavController, movieId: String?) {

    //get movie from id
    val movie: Movie = getMovies().first { it.id == movieId }
    //val user: User? = myList.find { it.userId == id }
    //source: https://stackoverflow.com/questions/51010592/kotlin-how-to-return-a-single-object-from-a-list-that-contains-a-specific-id

    val titleOfClickedMovie: String = movie.title
    Column(Modifier.fillMaxWidth()) {
        SimpleAppBar(title = titleOfClickedMovie, navController = navController)
        Column {
            MovieRow(movie)
            ImagesOfMovie(movie = movie)
        }
    }

}


@Composable
fun ImagesOfMovie(movie: Movie) {
    LazyRow {
        items(movie.images) { image ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(15.dp))
                        .size(200.dp),
                    //ask: how to make pics smaller - fill 50% of screen?
                    model = image,
                    contentDescription = "More Images",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
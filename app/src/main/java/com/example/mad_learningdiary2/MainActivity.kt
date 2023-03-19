package com.example.mad_learningdiary2

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies
import com.example.mad_learningdiary2.ui.theme.MAD_LearningDiary2Theme
import androidx.compose.runtime.* // remember
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role.Companion.Image
import coil.compose.AsyncImage


class MainActivity : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAD_LearningDiary2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyList()
                }
            }
        }
    }
}

@Preview
@Composable
fun MyList(movies: List<Movie> = getMovies()) {
    Column(Modifier.fillMaxWidth()) {
        Menu()
        LazyColumn {
            items(movies) { movie ->
                MovieRow(movie = movie)
            }
        }
    }
}


@Composable
fun MovieRow(movie: Movie) {

    // ARROW ICON: up = false (default),  down = true
    var arrowUpOrDown by remember {mutableStateOf(false)}
    var iconArrow = Icons.Default.KeyboardArrowDown
    if (arrowUpOrDown) { iconArrow = Icons.Default.KeyboardArrowUp}


    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .clip(shape = RoundedCornerShape(10.dp)),
    ) {
        Column {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
            ) {
              AsyncImage(
                  modifier = Modifier
                      .clip(shape = RoundedCornerShape(15.dp)),
                    model = movie.images[0],
                    contentDescription = "Poster of movie",
                    contentScale = ContentScale.Crop //so it's full
            )

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ){
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Add movie to favorites")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text(text = movie.title, style = MaterialTheme.typography.h6)
                Icon(
                    modifier = Modifier
                        .clickable { arrowUpOrDown = !arrowUpOrDown },
                    imageVector = iconArrow,
                    contentDescription = "Show details"
                )
            }
            Row() {
                AnimatedVisibility(visible = arrowUpOrDown) { //visible when arrow is clicked
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(text = "Director: ${movie.director}")
                        Text(text = "Release year: ${movie.year}")
                        Text(text = "Genre: ${movie.genre}")
                        Text(text = "Actors: ${movie.actors}")
                        Text(text = "Raiting: ${movie.rating}")
                        Divider(Modifier.padding(top = 20.dp, bottom = 20.dp))
                        Text(text = "Plot: ${movie.plot}", Modifier.padding(6.dp))

                        /* Director:
                        Released:
                        Genre:
                        Actors:
                        Raiting:
                        ______________________ (+ extra padding)
                        Plot */


                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun Menu() {
    var expandedState by remember {mutableStateOf(false)} //used in expanded card

    TopAppBar(
        title = { Text(text=" ") },
        actions = {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { expandedState = !expandedState },
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu"
                )
                DropdownMenu(
                    expanded = expandedState,
                    onDismissRequest = { expandedState = false }) {
                    DropdownMenuItem(onClick = {}) { //to be added
                        Icon(
                            contentDescription = "Favorite Icon",
                            imageVector = Icons.Filled.Favorite
                        )
                        Text(text = " Favorites")
                    }
                }
        }
    )
}
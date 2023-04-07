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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies
import com.example.mad_learningdiary2.widgets.HomeTopAppBar
import com.example.mad_learningdiary2.widgets.MovieRow

//prep code by leon
@Composable
fun HomeScreen(navController: NavController = rememberNavController()){
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
        MainContent(modifier = Modifier.padding(padding), navController = navController)
    }
}

@Composable
fun MainContent(
    modifier: Modifier,
    navController: NavController
) {
    val movies = getMovies()
    MovieList(
        modifier = modifier,
        navController = navController,
        movies = movies
    )
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    navController: NavController,
    movies: List<Movie> = getMovies()
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movies) { movie ->
            MovieRow(
                movie = movie,
                onItemClick = { movieId ->
                    navController.navigate(ScreenRoutes.Details.withId(movieId))
                }
            )
        }
    }
}



/*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies

@Composable
fun HomeScreen (navController: NavController) {
    Column(Modifier.fillMaxWidth()) {
        Menu(navController)
        MyList(navController = navController)
    }
}

@Composable
fun Menu(navController: NavController) { // blue app in the top of the screen
    var expandedState by remember { mutableStateOf(false) } //used in expanded card

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
                DropdownMenuItem(onClick = {
                    navController.navigate(route= ScreenRoutes.Favorites.route)
                }) { //to be added
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


@Composable
fun MyList(movies: List<Movie> = getMovies(), navController: NavController) {
    Column(Modifier.fillMaxWidth()) {
        LazyColumn {
            items(movies) { movie ->
                MovieRow(movie = movie) { movieId ->
                    //Log.d("Maincontent,", "Movie clicked --> $movieId")
                    // Replace the Log() call with a
                    navController.navigate(route= ScreenRoutes.Details.route + "/$movieId")
                }
            }
        }
    }
}


@Composable
fun MovieRow(movie: Movie, onItemClick : (String) -> Unit = {}) { // = {} default

    // ARROW ICON: up = false (default),  down = true
    var arrowUpOrDown by remember { mutableStateOf(false) }
    var iconArrow = Icons.Default.KeyboardArrowDown
    if (arrowUpOrDown) { iconArrow = Icons.Default.KeyboardArrowUp}


    Card(modifier = Modifier
        .clickable { onItemClick(movie.id) } //id of clicked movie
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
            Row {
                AnimatedVisibility(visible = arrowUpOrDown) { //visible when arrow is clicked
                    Description(movie = movie)
                }
            }
        }
    }
}

@Composable
fun Description(movie: Movie) {
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
    }
}
**/
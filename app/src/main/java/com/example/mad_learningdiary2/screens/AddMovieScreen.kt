package com.example.mad_learningdiary2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mad_learningdiary2.R
import com.example.mad_learningdiary2.models.Genre
import com.example.mad_learningdiary2.models.ListItemSelectable
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.viewModels.*
import com.example.mad_learningdiary2.widgets.SimpleTopAppBar

//prep code by leon


/**
 *
Users can add a new Movie to the Movie collection (AddMovieScreen). You can use the provided Composable as a template. Note: the template requires an Enum Class for Genres.
Extend your ViewModel with the following functionalities:
a)	Validate user input (use onValueChange listeners to call ViewModel functions):
-	title (String; not empty)
-	year (String; not empty)
-	genres (Enum Genre; at least 1 must be selected)
-	director (String, not empty)
-	actors (String, not empty)
-	plot (String)
-	rating (Float, not empty)
b)	Show an error text if user input is not valid
c)	“Add” Button:
-	Disabled by default
-	Enable it if all user input is valid
-	Add a movie to the collection onClick
Note: Movie images are not required. Show a placeholder for newly added Movies in your MovieRow.

 */
@Composable
fun AddMovieScreen(
    navController: NavController,
    movieViewModel: MoviesViewModel
){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.add_movie))
            }
        },
    ) { padding ->
        MainContent(Modifier.padding(padding), movieViewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(modifier: Modifier = Modifier, movieViewModel: MoviesViewModel) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            var title by remember {
                mutableStateOf("")
            }
            var titleIsValid by remember {
                mutableStateOf(false)
            }

            var year by remember {
                mutableStateOf("")
            }
            var yearIsValid by remember {
                mutableStateOf(false)
            }

            val genres = Genre.values().toList()

            var genreItems by remember {
                mutableStateOf(
                    genres.map { genre ->
                        ListItemSelectable(
                            title = genre.toString(),
                            isSelected = false
                        )
                    }
                )
            }
            var genreIsValid by remember {
                mutableStateOf(true) // <--- change to false by default
            }

            var director by remember {
                mutableStateOf("")
            }
            var directorIsValid by remember {
                mutableStateOf(false)
            }

            var actors by remember {
                mutableStateOf("")
            }
            var actorsIsValid by remember {
                mutableStateOf(false)
            }

            var plot by remember {
                mutableStateOf("")
            }
            var plotIsValid by remember {
                mutableStateOf(false)
            }

            var rating by remember {
                mutableStateOf("")
            }
            var ratingIsValid by remember {
                mutableStateOf(false)
            }

            var isEnabledSaveButton by remember {
                mutableStateOf(false) //???
            }

            OutlinedTextField(
                value = title,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    title = it
                    titleIsValid = validateInput(it)
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
                },
                label = { Text(text = stringResource(R.string.enter_movie_title)) },
                isError = false
            )

            OutlinedTextField(
                value = year,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    year = it
                    yearIsValid = validateInput(it)
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
                },
                label = { Text(stringResource(R.string.enter_movie_year)) },
                isError = false
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6)

            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(3)){
                items(genreItems) { genreItem ->
                    Chip(
                        modifier = Modifier.padding(2.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (genreItem.isSelected)
                                colorResource(id = R.color.purple_200)
                            else
                                colorResource(id = R.color.white)
                        ),
                        onClick = {
                            genreItems = genreItems.map {
                                if (it.title == genreItem.title) {
                                    genreItem.copy(isSelected = !genreItem.isSelected)
                                } else {
                                    it
                                }
                            }
                        }
                    ) {
                        Text(text = genreItem.title)
                    }
                }
            }

            OutlinedTextField(
                value = director,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    director = it
                    directorIsValid = validateInput(it)
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
                },
                label = { Text(stringResource(R.string.enter_director)) },
                isError = false
            )

            OutlinedTextField(
                value = actors,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    actors = it
                    actorsIsValid = validateInput(it)
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
                },
                label = { Text(stringResource(R.string.enter_actors)) },
                isError = false
            )

            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = {
                    plot = it
                    plotIsValid = validateIfString(it)
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
                },
                label = { Text(textAlign = TextAlign.Start, text = stringResource(R.string.enter_plot)) },
                isError = false
            )

            OutlinedTextField(
                value = rating,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    rating = if(it.startsWith("0")) {
                        ""
                    } else {
                        it
                    }
                    ratingIsValid = validateIfFloat(it.toFloat())
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)

                },
                label = { Text(stringResource(R.string.enter_rating)) },
                isError = false
            )

            //needs to be here.. otherwise it crashes??
            val newMovie: Movie = Movie(
                id = "?", //id??
                title = title,
                year = year,
                genre = getSelectedGenreList(genreList = genreItems),
                director = director,
                actors = actors,
                plot = plot,

                //place holder images:
                images = listOf("https://www.maricopa-sbdc.com/wp-content/uploads/2020/11/image-coming-soon-placeholder.png",
                    "https://www.maricopa-sbdc.com/wp-content/uploads/2020/11/image-coming-soon-placeholder.png",
                    "https://www.maricopa-sbdc.com/wp-content/uploads/2020/11/image-coming-soon-placeholder.png",
                    "https://www.maricopa-sbdc.com/wp-content/uploads/2020/11/image-coming-soon-placeholder.png"),
                rating = 7.0f
                //rating = rating.toFloat() // TODO somehow this makes it crash.. WHY?? validation needed first?
            )




            Button(
                enabled = isEnabledSaveButton,
                onClick = { movieViewModel.addMovie(newMovie)
                }) {
                Text(text = stringResource(R.string.add))
            }

        }
    }

}

fun validatedOrNotPlaceHolder (titleIsValid: Boolean, yearIsValid: Boolean, genreIsValid: Boolean, directorIsValid: Boolean, actorsIsValid: Boolean, plotIsValid:Boolean, ratingIsValid: Boolean ) : Boolean {
    return titleIsValid && yearIsValid && genreIsValid && directorIsValid && actorsIsValid && plotIsValid && ratingIsValid
    //else error message???

}
fun enableSaveButton (titleIsValid: Boolean, yearIsValid: Boolean, genreIsValid: Boolean, directorIsValid: Boolean, actorsIsValid: Boolean, plotIsValid:Boolean, ratingIsValid: Boolean ) : Boolean {
    return validatedOrNotPlaceHolder (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
}



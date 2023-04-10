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
import com.example.mad_learningdiary2.widgets.ErrorMessage
import com.example.mad_learningdiary2.widgets.SimpleTopAppBar

//prep code by leon

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

            var countForNewMovieID = 1; // increases when movie added to make id unique

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
                mutableStateOf(false)
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

            //button disabled by default
            var isEnabledSaveButton by remember {
                mutableStateOf(false)
            }

            //show error state --- could this be replaced only using the validate state??
            var yearShowErrorMessage by remember { mutableStateOf(false)}
            var titleShowErrorMessage by remember { mutableStateOf(false)}
            var genreShowErrorMessage by remember { mutableStateOf(false)}
            var directorShowErrorMessage by remember { mutableStateOf(false)}
            var actorsShowErrorMessage by remember { mutableStateOf(false)}
            var plotShowErrorMessage by remember { mutableStateOf(false)}
            var ratingShowErrorMessage by remember { mutableStateOf(false)}

/** TITLE input **/
            OutlinedTextField(
                value = title,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    title = it
                    titleIsValid = validateInput(it)
                    titleShowErrorMessage = !titleIsValid
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
                },
                label = { Text(text = stringResource(R.string.enter_movie_title)) },
                isError = titleShowErrorMessage
            )
            ErrorMessage(isError = titleShowErrorMessage, "title")

/** YEAR input **/
            OutlinedTextField(
                value = year,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    year = it
                    yearIsValid = validateInput(it)
                    yearShowErrorMessage = !yearIsValid
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)

                },
                label = {Text(stringResource(R.string.enter_movie_year)) },
                isError = yearShowErrorMessage,

            )
            ErrorMessage(isError = yearShowErrorMessage, "year")




/** GENRE input **/
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
                            genreIsValid = validateGenre(getSelectedGenreList(genreList = genreItems))
                            genreShowErrorMessage = !genreIsValid
                            isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)

                        }
                    ) {
                        Text(text = genreItem.title)
                    }
                }
            }

            ErrorMessage(isError = genreShowErrorMessage, "genre", "Choose at least 1 genre")

/** DIRECTOR input **/
            OutlinedTextField(
                value = director,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    director = it
                    directorIsValid = validateInput(it)
                    directorShowErrorMessage = !directorIsValid
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
                },
                label = { Text(stringResource(R.string.enter_director)) },
                isError = directorShowErrorMessage
            )
            ErrorMessage(isError = directorShowErrorMessage, "director")

/** ACTORS input **/
            OutlinedTextField(
                value = actors,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    actors = it
                    actorsIsValid = validateInput(it)
                    actorsShowErrorMessage = !actorsIsValid
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
                },
                label = { Text(stringResource(R.string.enter_actors)) },
                isError = actorsShowErrorMessage
            )
            ErrorMessage(isError = actorsShowErrorMessage, "actor")

/** PLOT input **/
            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = {
                    plot = it
                    plotIsValid = validateIfString(it)
                    plotShowErrorMessage = !plotIsValid
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
                },
                label = { Text(textAlign = TextAlign.Start, text = stringResource(R.string.enter_plot)) },
                isError = plotShowErrorMessage
            )
            ErrorMessage(isError = plotShowErrorMessage, "plot") //message?

/** RATING input **/
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
                    ratingIsValid = validateRating(it)
                    ratingShowErrorMessage = !ratingIsValid
                    isEnabledSaveButton = enableSaveButton (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)

                },
                label = { Text(stringResource(R.string.enter_rating)) },
                isError = ratingShowErrorMessage
            )
            ErrorMessage(isError = ratingShowErrorMessage, "rating", "Invalid. Choose a number between 0 and 10")

/** ADD NEW MOVIE **/

            Button(
                enabled = isEnabledSaveButton,
                onClick = {

                    val newMovie: Movie = Movie( //here otherwise added double
                        id = countForNewMovieID.toString(), // id?
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
                        //rating = 7.0f
                        rating = if (ratingIsValid) rating.toFloat() else 0f //needed so it doesnt crash at input
                    )
                    movieViewModel.addMovie(newMovie)
                    countForNewMovieID =+ 1 // so I get unique IDs for new movies


                }) {
                Text(text = stringResource(R.string.add))
            }

        }
    }

}


fun enableSaveButton (titleIsValid: Boolean, yearIsValid: Boolean, genreIsValid: Boolean, directorIsValid: Boolean, actorsIsValid: Boolean, plotIsValid:Boolean, ratingIsValid: Boolean ) : Boolean {
    return titleIsValid && yearIsValid && genreIsValid && directorIsValid && actorsIsValid && plotIsValid && ratingIsValid
}


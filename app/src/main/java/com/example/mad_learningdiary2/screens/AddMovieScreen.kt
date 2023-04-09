package com.example.mad_learningdiary2.screens

import androidx.compose.animation.AnimatedVisibility
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
-	genres (Enum Genre; at least 1 must be selected) TODO
-	director (String, not empty)
-	actors (String, not empty)
-	plot (String)
-	rating (Float, not empty) TODO
b)	Show an error text if user input is not valid - done
c)	“Add” Button:
-	Disabled by default - done
-	Enable it if all user input is valid - done
-	Add a movie to the collection onClick - done
Note: Movie images are not required. Show a placeholder for newly added Movies in your MovieRow. - done

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

            //  WHYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
            var rating by remember {
                mutableStateOf("")
            }
            var ratingIsValid by remember {
                mutableStateOf(false)
            }

            var isEnabledSaveButton by remember {
                mutableStateOf(false) //???
            }

            //show error state --- could this be replaced only using the validate state??
            var yearShowErrorMessage by remember { mutableStateOf(false)}
            var titleShowErrorMessage by remember { mutableStateOf(false)}
            var genreShowErrorMessage by remember { mutableStateOf(false)}
            var directorShowErrorMessage by remember { mutableStateOf(false)}
            var actorsShowErrorMessage by remember { mutableStateOf(false)}
            var plotShowErrorMessage by remember { mutableStateOf(false)}
            var ratingShowErrorMessage by remember { mutableStateOf(false)}

            //var ratingAsFloat = 0.0f
            var ratingAsFloat by remember { mutableStateOf(0f)}
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
            errorMessage(isError = titleShowErrorMessage, "title")

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

                    //errorMessage(isError = !yearIsValid)

                },
                label = {Text(stringResource(R.string.enter_movie_year)) },
                isError = yearShowErrorMessage,
/*
                keyboardActions = KeyboardActions(
                    onDone = {


                        //isError = !yearIsValid //show error line if is not valid



                        //enable here the button?
                    // validate here?
                    // isUserBelow18 = validateAge(inputText = value)
                    }
                ) */

            )
            errorMessage(isError = yearShowErrorMessage, "year")




/** GENRE input **/ //TODO add error
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
            errorMessage(isError = genreShowErrorMessage, "genre", "Choose at least 1 genre")

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
            errorMessage(isError = directorShowErrorMessage, "director")

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
            errorMessage(isError = actorsShowErrorMessage, "actor")

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
            errorMessage(isError = plotShowErrorMessage, "plot") //message?

/** RATING input **/ //TODO fix
            OutlinedTextField(
                value = rating,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {

                    //rating = if (it.toFloatOrNull() == null) {
                      //  0f.toString()
                    //} else {
                      //  it
                    //} //prevents crash when invalid? - i wish

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
            errorMessage(isError = ratingShowErrorMessage, "rating", "Invalid. Choose a number between 0 and 10")

/** NEW MOVIE **/
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
                //rating = 7.0f
                rating = if (ratingIsValid) rating.toFloat() else 0f

    // TODO somehow this makes it crash.. WHY?? validation needed first?
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

//TODO add var for which error?
fun validatedOrNotPlaceHolder (titleIsValid: Boolean, yearIsValid: Boolean, genreIsValid: Boolean, directorIsValid: Boolean, actorsIsValid: Boolean, plotIsValid:Boolean, ratingIsValid: Boolean ) : Boolean {
    return titleIsValid && yearIsValid && genreIsValid && directorIsValid && actorsIsValid && plotIsValid && ratingIsValid
    //else error message???

}
fun enableSaveButton (titleIsValid: Boolean, yearIsValid: Boolean, genreIsValid: Boolean, directorIsValid: Boolean, actorsIsValid: Boolean, plotIsValid:Boolean, ratingIsValid: Boolean ) : Boolean {
    return validatedOrNotPlaceHolder (titleIsValid, yearIsValid, genreIsValid, directorIsValid, actorsIsValid, plotIsValid, ratingIsValid)
}

@Composable
fun errorMessage (isError: Boolean, variable: String, message: String = "Invalid $variable entered") {
    AnimatedVisibility (isError) {
        Text(
            text = message,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp),

        )
    }

}
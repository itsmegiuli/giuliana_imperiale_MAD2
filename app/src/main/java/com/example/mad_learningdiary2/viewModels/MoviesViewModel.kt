package com.example.mad_learningdiary2.viewModels

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.mad_learningdiary2.models.Genre
import com.example.mad_learningdiary2.models.ListItemSelectable
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies
import com.example.mad_learningdiary2.screens.*
import java.lang.Float


//lecture task: implement a ViewModel that will handle a collection of ALL movies and movies that are marked as FAVORITE
//functionalities:
// toggle the is favorite state of a movie ✓
// get list of all FAV movies ✓
// get list of ALL movies ✓
class MoviesViewModel : ViewModel() {
    private val _movieList = getMovies().toMutableList() // get all movies and create an observable list
    val movieList: List<Movie> //expose previously created list but immutable
    get() = _movieList

    fun toggleFavoriteState(movie: Movie) {
        val movieFound = _movieList.find { it.id == movie.id}
        if (movieFound != null) {
            movieFound.isFavorite = !movieFound.isFavorite
        }
        Log.i("toggleFavState", movie.title + " is favorite? ${movie.isFavorite}")
    }

    fun getFavorites (): List<Movie> {
        return _movieList.filter { it.isFavorite }
    }

    fun getAllMovies () : List<Movie> {
        return _movieList
    }

    fun filterMovie (movieId: String): Movie {
        return _movieList.filter { it.id == movieId}[0]
    }

    fun addMovie (movie: Movie) {
        _movieList.add(movie)
    }
}

//add movie
fun getSelectedGenreList (genreList: List<ListItemSelectable>): MutableList<Genre> {
    val selectedGenreList: MutableList<Genre> = mutableListOf()
    for (genre in genreList) {
        if (genre.isSelected) {
            selectedGenreList.add(enumValueOf(genre.title)) // TODO fix type mismatch --> how to get genre here???
        }
    }
    return selectedGenreList
}

//refactor??
/*
fun validateInput (toValidate : String?): Boolean {
    return if (toValidate != null) {
        if(toValidate.isNotEmpty() ) {
            Log.i("validation", "$toValidate is validated")
            true
        } else {
            Log.i("validation", "$toValidate is not validated")
            false
        }
    } else {
        false
    }
}

*/


fun validateInput (toValidate : String): Boolean {
    return if(toValidate.isNotEmpty() ) {
            Log.i("validation", "$toValidate is validated")
            true
        } else {
            Log.i("validation", "$toValidate is not validated")
            false
        }
}

fun validateIfString (toValidate: Any) : Boolean {
    return toValidate is String // returns true if tovalidate is string
}

fun validateIfFloat (toValidate: Any) : Boolean {
    return toValidate is kotlin.Float
}
//string & not empty: title, year, director, actors - done
//string: plot (can be empty) - done
//rating: float, not empty -
//genres: at least 1 enum selected



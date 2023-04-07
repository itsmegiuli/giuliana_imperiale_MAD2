package com.example.mad_learningdiary2.viewModels

import androidx.lifecycle.ViewModel
import com.example.mad_learningdiary2.models.Movie
import com.example.mad_learningdiary2.models.getMovies

class MoviesViewModel : ViewModel() {
    private val _movieList = getMovies().toMutableList() // get all movies and create an observable list
    val movieList: List<Movie> //expose previously created list but immutable
    get() = _movieList

    //logic
}
package com.example.newmovlex.feature.movie.displayMovies.view

import com.example.newmovlex.ui.models.MovieModel

interface MovieView {
    fun returnMovie(movie: ArrayList<MovieModel>, num: Int)
    fun showErrorMessage(message: String?)
}
package com.example.newmovlex.feature.movie.searchMovie.view

import com.example.newmovlex.ui.models.SearchModel

interface SearchMovieView {
    fun returnMovie(movie: SearchModel)
    fun showErrorMessage(message: String?)
}
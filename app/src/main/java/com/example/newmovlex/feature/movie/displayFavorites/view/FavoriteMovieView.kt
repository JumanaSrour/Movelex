package com.example.newmovlex.feature.movie.displayFavorites.view

import com.example.newmovlex.ui.models.FavoriteModel
import com.example.newmovlex.ui.models.FavoriteMovieList
import com.example.newmovlex.ui.models.MovieModel

interface FavoriteMovieView {
    fun returnMovie(movie: ArrayList<MovieModel>, num: Int)
    fun returnFavoriteMovie(favoriteModel: ArrayList<FavoriteModel>)
    fun showErrorMsg(message: String?)
}
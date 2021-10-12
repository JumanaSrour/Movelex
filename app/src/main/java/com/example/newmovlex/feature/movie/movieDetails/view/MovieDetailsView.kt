package com.example.newmovlex.feature.movie.movieDetails.view

import com.example.newmovlex.ui.models.MovieModel
import com.example.newmovlex.ui.models.Review

interface MovieDetailsView {
    fun returnMovie(movieModel: MovieModel)
    fun returnReviews(review: ArrayList<Review>, num: Int)
    fun returnReview(review: Review)
    fun showErrorMessage(message: String)
}
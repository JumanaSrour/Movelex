package com.example.newmovlex.feature.movie.movieDetails.view

import com.example.newmovlex.ui.models.Review

interface MovieReviewsView {
    fun returnReviews(review: Review){}
    fun showErrorMessage(message: String){}
}
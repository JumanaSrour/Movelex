package com.example.newmovlex.feature.movie.movieDetails.presenter

import android.app.Activity
import androidx.collection.ArrayMap
import com.example.newmovlex.feature.movie.movieDetails.view.MovieDetailsView
import com.example.newmovlex.network.Constants
import com.example.newmovlex.network.Constants.Companion.movie_id
import com.example.newmovlex.network.Constants.Companion.review_id
import com.example.newmovlex.network.Constants.Companion.text
import com.example.newmovlex.network.features.MovieRequest
import com.example.newmovlex.network.features.PaginationListener
import com.example.newmovlex.network.features.RequestListener
import com.example.newmovlex.ui.models.MovieModel
import com.example.newmovlex.ui.models.Review

data class MovieDetailsPresenter(val mActivity: Activity, val mView: MovieDetailsView) {
    fun getMovieDetails(movie: MovieModel) {
        MovieRequest().getMovieDetails("${movie.id}", object : RequestListener<MovieModel?> {


            override fun onFail(message: String?, code: Int) {
                mView.showErrorMessage(message!!)
            }

            override fun onSuccess(data: MovieModel?) {
                mView.returnMovie(data!!)
            }

        })
    }

    fun getMovieReviews(page: Int) {
        val params = ArrayMap<String, Any>()
        params["page_size"] = Constants.page_size
        params["page_number"] = page
        params["movie_id"] = movie_id

        MovieRequest().getMovieReviews(params, object : PaginationListener<ArrayList<Review>> {
            override fun onSuccess(data: ArrayList<Review>, num: Int) {
                mView.returnReviews(data, num)
            }

            override fun onFailure(message: String) {
                mView.showErrorMessage(message)
            }


        })
    }

    fun getMovieReview() {
        val params = ArrayMap<String, Any>()
        params["movie_id"] = movie_id
        params["text"] = text
        params["review_id"] = review_id

        MovieRequest().getMovieReview(params, object : RequestListener<Review> {
            override fun onSuccess(data: Review) {
                mView.returnReview(data)
            }

            override fun onFail(message: String?, code: Int) {
                mView.showErrorMessage(message!!)
            }

        })
    }

}
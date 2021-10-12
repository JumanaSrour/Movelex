package com.example.newmovlex.feature.movie.displayMovies.presenter

import android.app.Activity
import android.text.TextUtils
import androidx.collection.ArrayMap
import com.example.newmovlex.feature.movie.displayMovies.view.MovieView
import com.example.newmovlex.network.Constants
import com.example.newmovlex.network.Constants.Companion.page_size
import com.example.newmovlex.network.features.MovieRequest
import com.example.newmovlex.network.features.PaginationListener
import com.example.newmovlex.ui.models.MovieModel

data class MoviePresenter(val mActivity: Activity, val mView: MovieView) {
    fun getListOfMovies(page: Int, stringFromEditText: String) {
        val params = ArrayMap<String, Any>()
        params["page_size"] = page_size
        params["page_number"] = page
        params["name"] = stringFromEditText
        if (!TextUtils.isEmpty(stringFromEditText)) {
            params["name"] = stringFromEditText
        }
        MovieRequest().getListOfMovies(params, object : PaginationListener<ArrayList<MovieModel>> {
            override fun onSuccess(data: ArrayList<MovieModel>, num: Int) {
                mView.returnMovie(data, num)
            }

            override fun onFailure(message: String) {
                mView.showErrorMessage(message)
            }

        })
    }
}
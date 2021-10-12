package com.example.newmovlex.feature.movie.displayFavorites.view

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.collection.ArrayMap
import com.example.newmovlex.network.Constants
import com.example.newmovlex.network.Constants.Companion.movie_id
import com.example.newmovlex.network.features.MovieRequest
import com.example.newmovlex.network.features.PaginationListener
import com.example.newmovlex.network.features.RequestListener
import com.example.newmovlex.ui.models.FavoriteModel
import com.example.newmovlex.ui.models.MovieModel
import com.example.newmovlex.utils.AppSharedFunctions

data class FavoritesPresenter(val mActivity: Activity, val mView: FavoriteMovieView) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun getListOfFavoriteMovies(page: Int) {
        if (AppSharedFunctions.networkIsConnected(mActivity)) {
            val params = ArrayMap<String, Any>()
            params["page_size"] = Constants.page_size
            params["page_number"] = page
            MovieRequest().getListOfFavoriteMovies(params, object :
                PaginationListener<ArrayList<MovieModel>> {
                override fun onSuccess(data: ArrayList<MovieModel>, num: Int) {
                    mView.returnMovie(data, num)
                }

                override fun onFailure(message: String) {
                    mView.showErrorMsg(message)
                }

            })
        }
    }

    fun favNotFavMovie(){
        val params = ArrayMap<String, Any>()
        params["movie_id"] = movie_id
        MovieRequest().favNotFavMovie(params, object : RequestListener<ArrayList<FavoriteModel>>{
            override fun onFail(message: String?, code: Int) {
                mView.showErrorMsg(message)
            }

            override fun onSuccess(data: ArrayList<FavoriteModel>) {
                mView.returnFavoriteMovie(data!!)
            }

        })
    }
}

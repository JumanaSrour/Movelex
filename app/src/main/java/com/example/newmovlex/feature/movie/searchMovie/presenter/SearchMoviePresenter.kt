package com.example.newmovlex.feature.movie.searchMovie.presenter

import android.app.Activity
import android.widget.EditText
import androidx.collection.ArrayMap
import com.example.newmovlex.feature.movie.searchMovie.view.SearchMovieView
import com.example.newmovlex.network.Constants
import com.example.newmovlex.network.features.MovieRequest
import com.example.newmovlex.network.features.RequestListener
import com.example.newmovlex.ui.models.MovieModel
import com.example.newmovlex.ui.models.SearchModel
import com.example.newmovlex.utils.AppSharedFunctions

data class SearchMoviePresenter(val mActivity: Activity, val mView: SearchMovieView) {

    fun search(
        search: EditText
        ){
        if (AppSharedFunctions.checkEditTextIsEmpty(search)) {
            AppSharedFunctions.showErrorField(search, "This field is required")
            return
        }
        searchMovie(
            AppSharedFunctions.getStringFromEditText(search)
        )
    }

    private fun searchMovie(search: String) {
        val params = ArrayMap<String, Any>()
        params["search"] = Constants.SEARCH
        MovieRequest().getSearchMovieItem(params, object : RequestListener<SearchModel> {
            override fun onFail(message: String?, code: Int) {
                mView.showErrorMessage(message)
            }

            override fun onSuccess(data: SearchModel) {
                mView.returnMovie(data)
            }


        })
    }
}
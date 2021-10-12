package com.example.newmovlex.network.features

import android.util.Log
import androidx.collection.ArrayMap
import com.example.newmovlex.network.RetrofitService
import com.example.newmovlex.ui.models.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRequest {
    private val retrofitService = RetrofitService()
    private val gson = Gson()

    fun getListOfMovies(
        map: ArrayMap<String, Any>,
        listener: PaginationListener<ArrayList<MovieModel>>,
    ) {
        retrofitService.getListOfMovies(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(
                call: Call<AppResponse>,
                response: Response<AppResponse>,
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status) {
                        Log.d("----", "onResponse: ${response.body()?.items.toString()}")
                        val movieList: MovieList =
                            gson.fromJson(response.body()?.getResult(), MovieList::class.java)
                        listener.onSuccess(movieList.data, movieList.total_records)

                    } else {
                        response.body()
                            ?.let {
                                response.body()?.message?.let { it1 ->
                                    listener.onFailure(
                                        it1
                                    )
                                }
                            }
                    }
                } else {
                    listener.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e("---", "onFailure: $t")
            }

        })
    }

    fun getListOfFavoriteMovies(
        map: ArrayMap<String, Any>,
        listener: PaginationListener<ArrayList<MovieModel>>,
    ) {
        retrofitService.getListOfFavoriteMovies(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status) {
                        Log.d("----", "onResponse: ${response.body()?.items.toString()}")
                        val favoriteMovieList: MovieList =
                            gson.fromJson(response.body()?.getResult(),
                                MovieList::class.java)
                        listener.onSuccess(favoriteMovieList.data, favoriteMovieList.total_records)
                    } else {
                        response.body()
                            ?.let {
                                response.body()?.message?.let { it1 ->
                                    listener.onFailure(
                                        it1
                                    )
                                }
                            }
                    }
                } else {
                    listener.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e("---", "onFailure: $t")
            }

        })
    }

    fun favNotFavMovie(
        map: ArrayMap<String, Any>,
        listener: RequestListener<ArrayList<FavoriteModel>>,
    ) {
        retrofitService.favUnFavMovie(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status) {
                        Log.d("----", "onResponse: ${response.body()?.items.toString()}")
                        Log.d("----", "onResponse: ${response.body()?.items.toString()}")
//                        val favoriteMovieList: FavoriteMovieList =
//                            gson.fromJson(response.body()?.getResult(),
//                                FavoriteMovieList::class.java)
//                        listener.onSuccess(favoriteMovieList.data)
                    } else {
                        response.body()
                            ?.let {
                                response.body()?.message?.let { it1 ->
                                    listener.onFail(
                                        it1, response.code()
                                    )
                                }
                            }
                    }
                } else {
                    listener.onFail(response.message(), response.code())
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e("---", "onFailure: $t")
            }

        })
    }

    fun getSearchMovieItem(
        map: ArrayMap<String, Any>,
        listener: RequestListener<SearchModel>,
    ) {
        retrofitService.getSearchMovieResult(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status) {
                        Log.d("----", "onResponse: ${response.body()?.items.toString()}")
                        val movieModel: SearchModel =
                            gson.fromJson(response.body()?.getResult(), SearchModel::class.java)
                        listener.onSuccess(movieModel)
                    } else {
                        response.body()
                            ?.let {
                                listener.onFail(response.body()?.message, response.code())
                            }
                    }
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e("---", "onFailure: $t")
            }

        })
    }

    fun getMovieDetails(map: String, listener: RequestListener<MovieModel?>) {
        retrofitService.getMovieDetails(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status) {
                        Log.d(
                            "ttt",
                            "response.body(): " + response.body()?.items.toString() + ""
                        )
                        val movie: MovieModel =
                            gson.fromJson(response.body()?.getResult(), MovieModel::class.java)
                        listener.onSuccess(movie)
                    } else {
                        response.body()
                            ?.let { listener.onFail(response.body()?.message, it.status_code) }
                    }
                } else {
                    listener.onFail(response.message(), response.code())
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e(
                    "ttt", t.message.toString() + ""
                )
            }

        })
    }

    fun getMovieReview(map: ArrayMap<String, Any>, listener: RequestListener<Review>) {
        retrofitService.getMovieReview(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status) {
                        Log.d(
                            "ttt",
                            "response.body(): " + response.body()?.items.toString() + ""
                        )
                        val review: Review =
                            gson.fromJson(response.body()?.getResult(), Review::class.java)
                        listener.onSuccess(review)
                    } else {
                        response.body()
                            ?.let { listener.onFail(response.body()?.message, it.status_code) }
                    }
                } else {
                    listener.onFail(response.message(), response.code())
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e(
                    "ttt", t.message.toString() + ""
                )
            }

        })
    }

    fun getMovieReviews(
        map: ArrayMap<String, Any>,
        listener: PaginationListener<ArrayList<Review>>,
    ) {
        retrofitService.getMovieReviews(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status) {

                        Log.d("----", "onResponse: ${response.body()?.items.toString()}")

                        val review: ReviewList =
                            gson.fromJson(response.body()?.getResult(), ReviewList::class.java)
                        listener.onSuccess(review.data, review.total_records)
                    } else {
                        response.body()
                            ?.let {
                                response.body()?.message?.let { it1 ->
                                    listener.onFailure(
                                        it1
                                    )
                                }
                            }
                    }
                } else {
                    listener.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e(
                    "ttt", t.message.toString() + ""
                )
            }

        })
    }

}
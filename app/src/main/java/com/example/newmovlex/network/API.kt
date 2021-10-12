package com.example.newmovlex.network

import androidx.collection.ArrayMap
import com.example.newmovlex.ui.models.AppResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface API {
    //UserOperations
    @FormUrlEncoded
    @POST("signup")
    fun createUser(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUserCall(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @FormUrlEncoded
    @POST("forget")
    fun forgetPasswordUser(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @FormUrlEncoded
    @POST("logout")
    fun logoutUser(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @GET("profile")
    fun userProfile(): Call<AppResponse>


    // Movies Api
    @FormUrlEncoded
    @POST("movies")
    fun listOfMovies(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @FormUrlEncoded
    @POST("movies")
    fun listOfFavoriteMovies(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @FormUrlEncoded
    @POST("movie")
    fun searchMovie(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @FormUrlEncoded
    @POST("reviews")
    fun getMovieReviews(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @FormUrlEncoded
    @POST("review")
    fun getMovieReview(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @FormUrlEncoded
    @POST("favorite")
    fun favoriteOrNotFavoriteMovie(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @GET("intros")
    fun getIntros(
        @FieldMap map: ArrayMap<String, Any>,
    ): Call<AppResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: String): Call<AppResponse>


}
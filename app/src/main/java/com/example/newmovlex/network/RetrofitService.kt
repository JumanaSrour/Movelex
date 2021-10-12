package com.example.newmovlex.network

import androidx.collection.ArrayMap
import com.example.newmovlex.network.Constants.Companion.BASE_URL
import com.example.newmovlex.ui.models.AppResponse
import com.example.newmovlex.ui.models.User
import com.example.newmovlex.utils.storage.SharedPrefManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {

    private lateinit var api: API

    init {
        try {
            val interceptorToHeaderData = Interceptor { chain ->
                val builder = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        if (SharedPrefManager.isLoggedIn) "Bearer" + " " +
                                SharedPrefManager.token.access_token else ""
                    )
                    .build()
                chain.proceed(builder)
            }
            init(interceptorToHeaderData)
        } catch (e: Exception) {

        }
    }

    private fun init(interceptor: Interceptor) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(6000, TimeUnit.MILLISECONDS)
            .build()


        this.api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient)
            .build()
            .create(API::class.java)
    }


    // POST operations for UserRequest features
    fun createUser(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.createUser(map)
    }

    fun loginUsercall(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.loginUserCall(map)
    }

    fun logoutUser(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.logoutUser(map)
    }

    fun forgetPassword(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.forgetPasswordUser(map)
    }

    // POST operations for MovieRequest features
    fun getListOfMovies(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.listOfMovies(map)
    }

    fun getListOfFavoriteMovies(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.listOfFavoriteMovies(map)
    }

    fun getSearchMovieResult(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.searchMovie(map)
    }

    fun getMovieReviews(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.getMovieReviews(map)
    }

    fun getMovieReview(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.getMovieReview(map)
    }

    fun favUnFavMovie(map: ArrayMap<String, Any>): Call<AppResponse> {
        return api.favoriteOrNotFavoriteMovie(map)
    }

    fun getMovieDetails(map: String): Call<AppResponse> {
        return api.getMovieDetails(map)
    }


    // GET operations for UserRequest features
    fun userProfile(): Call<AppResponse> {
        return api.userProfile()
    }


}
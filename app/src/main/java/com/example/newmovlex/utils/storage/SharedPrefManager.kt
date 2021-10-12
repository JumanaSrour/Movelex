package com.example.newmovlex.utils.storage

import android.annotation.SuppressLint
import android.content.Context

import com.example.newmovlex.utils.MovlexApp
import com.google.gson.Gson
import android.preference.PreferenceManager

import android.content.SharedPreferences
import com.example.newmovlex.ui.models.*


class SharedPrefManager() {
    companion object {
        var gson = Gson()
        val isLoggedIn: Boolean
            get() {
                val sharedPreferences =
                    MovlexApp.getInstance()
                        .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                return sharedPreferences.getBoolean(SHARED_IS_LOGIN, false)
            }

        val isFirstOpen: Boolean
            get() {
                val sharedPreferences =
                    MovlexApp.getInstance()
                        .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                return sharedPreferences.getBoolean(SHARED_IS_FIRST_OPEN, true)
            }

        val user: User
            get() {
                return gson.fromJson(
                    MovlexApp.getInstance()
                        .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(
                            SHARED_USER, ""
                        ), User::class.java
                )
            }

        val token: TokenBean
            get() {
                gson = Gson()
                return gson.fromJson(
                    MovlexApp.getInstance()
                        .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(
                            SHARED_TOKEN, null
                        ), TokenBean::class.java
                )
            }

        val movie: MovieModel
            get() {
                gson = Gson()
                return gson.fromJson(
                    MovlexApp.getInstance()
                        .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(
                            SAHRED_MOVIE,null
                        ), MovieModel::class.java
                )
            }

        fun saveMovie(movie: MovieModel) {
            MovlexApp.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(SAHRED_MOVIE, gson.toJson(movie.id)).apply()
        }

        fun setLogin(isLogin: Boolean) {
            MovlexApp.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(SHARED_IS_LOGIN, isLogin).apply()
        }

        fun setFirstOpen(isFirstOpen : Boolean){
            MovlexApp.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(SHARED_IS_FIRST_OPEN, isFirstOpen).apply()
        }

        fun saveUser(user: User) {
            MovlexApp.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(SHARED_USER, gson.toJson(user)).apply()
        }

        fun saveToken(token: TokenBean) {
            MovlexApp.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(SHARED_TOKEN, gson.toJson(token)).apply()
        }

        fun clear() {
            val sharedPreferences =
                MovlexApp.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        }


        private val SHARED_PREF_NAME = "movlix"
        private val SHARED_USER = "user"
        private val SHARED_TOKEN = "token"
        private val SAHRED_MOVIE = "movie"
        private val SHARED_IS_LOGIN = "isLogin"
        private val SHARED_IS_FIRST_OPEN = "isFirstOpen"

        @SuppressLint("StaticFieldLeak")
        private var mInstance: SharedPrefManager? = null

    }

}
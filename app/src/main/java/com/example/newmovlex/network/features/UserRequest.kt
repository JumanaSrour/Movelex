package com.example.newmovlex.network.features


import android.util.Log
import androidx.collection.ArrayMap
import com.example.newmovlex.feature.user.signup.view.SignupView
import com.example.newmovlex.network.RetrofitService
import com.example.newmovlex.ui.models.*
import com.example.newmovlex.utils.storage.SharedPrefManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRequest {
    private val retrofitService = RetrofitService()
    private val gson = Gson()
    private val signupView: SignupView? = null


    fun createUser(map: ArrayMap<String, Any>, mRequestListener: RequestListener<User?>) {
        retrofitService.createUser(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    Log.d(
                        "ttt",
                        "response.body(): " + response.body()?.items.toString()+ ""
                    )
                    val user: SignupResponse = gson.fromJson(response.body()?.getResult(), SignupResponse::class.java)
                    SharedPrefManager.setLogin(true)
                    mRequestListener.onSuccess(user.items as User)
                } else {
                    response.body()
                        ?.let { mRequestListener.onFail(response.body()?.message, it.status_code) }
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e(
                    "ttt", t.message.toString() + ""
                )
            }

        })
    }

    fun loginUsercall(map: ArrayMap<String, Any>, mRequestListener: RequestListener<User?>) {

        retrofitService.loginUsercall(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    Log.d(
                        "ttt",
                        "response.body(): " + response.body()?.items.toString() + ""
                    )
                    val user: LoginResponse =
                        gson.fromJson(response.body()?.getResult(), LoginResponse::class.java)
                    SharedPrefManager.saveUser(user.user)
                    SharedPrefManager.saveToken(user.token)
                    SharedPrefManager.setLogin(true)
                    mRequestListener.onSuccess(user.user)

                } else {
                    response.body()
                        ?.let { mRequestListener.onFail(response.body()?.message, it.status_code) }
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e(
                    "ttt", t.message.toString() + ""
                )
                //mRequestListener.onFail(Utils.getThrowableErrorMsg(throwable), 0)
            }

        })


    }


    fun forgetPassword(map: ArrayMap<String, Any>, mRequestListener: RequestListener<User?>) {

        retrofitService.forgetPassword(map).enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    Log.d(
                        "ttt",
                        "response.body(): " + response.body()?.items.toString().toString() + ""
                    )
                    val user: User = gson.fromJson(response.body()?.getResult(), User::class.java)
                    mRequestListener.onSuccess(user)
                } else {
                    response.body()
                        ?.let { mRequestListener.onFail(response.body()?.message, it.status_code) }
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e(
                    "ttt", t.message.toString() + ""
                )
                //mRequestListener.onFail(Utils.getThrowableErrorMsg(throwable), 0)
            }

        })


    }

    fun logoutUser(map: ArrayMap<String, Any>, listener: RequestListener<User?>){
        retrofitService.logoutUser(map).enqueue(object : Callback<AppResponse>{
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    Log.d(
                        "ttt",
                        "response.body(): " + response.body()?.items.toString().toString() + ""
                    )
                    val user: LoginResponse =
                        gson.fromJson(response.body()?.getResult(), LoginResponse::class.java)
                    SharedPrefManager.setLogin(false)
                    listener.onSuccess(user.user)

                } else {
                    response.body()
                        ?.let { listener.onFail(response.body()?.message, it.status_code) }
                }
            }

            override fun onFailure(call: Call<AppResponse>, t: Throwable) {
                Log.e(
                    "ttt", t.message.toString() + ""
                )
            }

        })
    }

    fun getUserProfile(listener: RequestListener<User?>) {
        retrofitService.userProfile().enqueue(object : Callback<AppResponse> {
            override fun onResponse(call: Call<AppResponse>, response: Response<AppResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status) {
                        Log.d(
                            "ttt",
                            "response.body(): " + response.body()?.items.toString() + ""
                        )
                        val user: User =
                            gson.fromJson(response.body()?.getResult(), User::class.java)
                        listener.onSuccess(user)
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
}


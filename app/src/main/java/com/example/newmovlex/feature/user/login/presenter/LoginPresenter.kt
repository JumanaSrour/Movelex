package com.example.newmovlex.feature.user.login.presenter


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap

import com.example.newmovlex.R
import com.example.newmovlex.feature.movie.displayMovies.view.HomeMoviesActivity
import com.example.newmovlex.feature.user.login.view.LoginView
import com.example.newmovlex.network.Constants
import com.example.newmovlex.network.features.RequestListener
import com.example.newmovlex.network.features.UserRequest
import com.example.newmovlex.ui.models.User
import com.example.newmovlex.utils.AppSharedFunctions
import com.example.newmovlex.utils.AppSharedFunctions.Companion.checkEditTextIsEmpty
import com.example.newmovlex.utils.AppSharedFunctions.Companion.getStringFromEditText
import com.example.newmovlex.utils.AppSharedFunctions.Companion.networkIsConnected
import com.example.newmovlex.utils.AppSharedFunctions.Companion.showErrorField


class LoginPresenter(val mActivity: Activity, var mView: LoginView):
    AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    public fun login(
        username: EditText,
        password: EditText
    ) {
        if (checkEditTextIsEmpty(username)) {
            showErrorField(username, "This field is required")
            return
        }
        if (checkEditTextIsEmpty(password)) {
            showErrorField(password, getString(R.string.required))
            return
        }

        loginAccount(
            getStringFromEditText(username),
            getStringFromEditText(password)
        )

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun loginAccount(
        username: String,
        password: String
    ) {

        if (networkIsConnected(mActivity)) {


            val params = ArrayMap<String, Any>()
            params["username"] = username
            params["password"] = password
            params["device_token"] = Constants.deviceToken
            params["device_id"] = AppSharedFunctions.getDeviceId(mActivity)
            params["device_type"] = Constants.deviceType
            params["client_id"] = Constants.clientId
            params["client_secret"] = Constants.clientSecret
            params["grant_type"] = Constants.grantType

         UserRequest().loginUsercall(params,object :RequestListener<User?>{
             override fun onFail(message: String?, code: Int) {
                 mView.showErrorMsg(message.toString())
             }
             override fun onSuccess(data: User?) {

                 mActivity.startActivity(Intent(mActivity, HomeMoviesActivity::class.java))
                 finish()
             }

         })


//            UserRequest().loginUser(params, object : Callback<AppResponse> {
//
//                override fun onFailure(call: Call<AppResponse>, t: Throwable) {
//                    mView.showErrorMsg(t.message)
//                }
//
//                override fun onResponse(
//                    call: Call<AppResponse>,
//                    response: Response<AppResponse>
//                ) {
//                    val appResponse = response.body()
//                    val data = gson.fromJson(
//                        appResponse?.getResult(),
//                        LoginResponse::class.java
//                    ) as LoginResponse
//                    mView.returnUser(data.user)
//                    Log.d("ttt", "onResponse: ")
////                    mActivity.startActivity(Intent(mActivity, HomeMoviesActivity::class.java))
////                   mActivity.finish()
//                }
//
//            })
        }
    }
}





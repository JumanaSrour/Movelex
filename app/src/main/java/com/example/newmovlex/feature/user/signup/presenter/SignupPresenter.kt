package com.example.newmovlex.feature.user.signup.presenter

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.collection.ArrayMap
import com.example.newmovlex.feature.movie.displayMovies.view.HomeMoviesActivity
import com.example.newmovlex.feature.user.signup.view.SignupView
import com.example.newmovlex.network.Constants
import com.example.newmovlex.network.features.RequestListener
import com.example.newmovlex.network.features.UserRequest
import com.example.newmovlex.ui.models.User
import com.example.newmovlex.utils.AppSharedFunctions
import com.example.newmovlex.utils.AppSharedFunctions.Companion.getStringFromEditText

data class SignupPresenter(val mActivity: Activity, var mView: SignupView) {
    @RequiresApi(Build.VERSION_CODES.M)
    public fun signup(
        edName: EditText,
        email: EditText,
        password: EditText,
        confirmPassword: EditText
    ) {
        edName.error = null
        email.error = null
        password.error = null
        confirmPassword.error = null
        if (AppSharedFunctions.checkEditTextIsEmpty(edName)) {
            AppSharedFunctions.showErrorField(edName, "this field is required")
            return
        }
        if (AppSharedFunctions.checkEditTextIsEmpty(email)) {
            AppSharedFunctions.showErrorField(email, "this field is required")
            return
        }
        if (AppSharedFunctions.checkEditTextIsEmpty(password)) {
            AppSharedFunctions.showErrorField(password, "this field is required")
            return
        }
        if (AppSharedFunctions.checkPasswordIsMatch(password, confirmPassword)){
            AppSharedFunctions.showErrorField(confirmPassword, "Passwords confirmation doesn't match, please make sure passwords are same")
            return
        }
        if (AppSharedFunctions.checkEditTextIsEmpty(confirmPassword)) {
            AppSharedFunctions.showErrorField(confirmPassword, "this field is required")
            return
        }
        singUpUser(
            getStringFromEditText(edName),
            getStringFromEditText(email),
            getStringFromEditText(password),
            getStringFromEditText(confirmPassword)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun singUpUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (AppSharedFunctions.networkIsConnected(mActivity)) {

            val params = ArrayMap<String, Any>()
            params["name"] = name
            params["email"] = email
            params["password"] = password
            params["password_confirmation"] = confirmPassword
            params["device_token"] = Constants.deviceToken
            params["device_id"] = AppSharedFunctions.getDeviceId(mActivity)
            params["device_type"] = Constants.deviceType
            params["client_id"] = Constants.clientId
            params["client_secret"] = Constants.clientSecret
            params["grant_type"] = Constants.grantType

            UserRequest().createUser(params,object : RequestListener<User?> {
                override fun onFail(message: String?, code: Int) {
                    mView.showErrorMsg(message.toString())
                }

                override fun onSuccess(data: User?) {
                    mActivity.startActivity(Intent(mActivity, HomeMoviesActivity::class.java))
                }

            })
        }

    }
}
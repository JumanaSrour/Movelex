package com.example.newmovlex.feature.user.profile.presenter

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.newmovlex.feature.user.profile.view.ProfileView
import com.example.newmovlex.network.features.RequestListener
import com.example.newmovlex.network.features.UserRequest
import com.example.newmovlex.ui.models.User
import com.example.newmovlex.utils.AppSharedFunctions

data class ProfilePresenter( val mActivity: Activity, val mView: ProfileView) {
    @RequiresApi(Build.VERSION_CODES.M)
    public fun getUserProfile(){
        if (AppSharedFunctions.networkIsConnected(mActivity)){
            UserRequest().getUserProfile(object : RequestListener<User?> {
                override fun onFail(message: String?, code: Int) {
                    mView.showErrorMsg(message.toString())
                }

                override fun onSuccess(data: User?) {
                    Log.e("---", "onSuccess: $data", )

                }


            })
        }
    }
}
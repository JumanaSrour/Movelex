package com.example.newmovlex.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi

class AppSharedFunctions {
    companion object {
        fun checkEditTextIsEmpty(editText: EditText): Boolean {
            return TextUtils.isEmpty(editText.text.toString())
        }

        fun checkPasswordIsMatch(editText: EditText, editText_second: EditText): Boolean{
            return editText == editText_second
        }

        fun showErrorField(editText: EditText, msg: String) {
            editText.error = msg
        }

        fun getStringFromEditText(editText: EditText): String {
            return editText.text.toString()
        }

        @SuppressLint("HardwareIds")
        fun getDeviceId(activity: Activity): String {
            return Settings.Secure.getString(
                activity.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun networkIsConnected(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

    }
}
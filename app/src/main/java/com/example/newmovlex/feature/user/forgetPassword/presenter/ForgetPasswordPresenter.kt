package com.example.newmovlex.feature.user.forgetPassword.presenter

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.newmovlex.feature.user.forgetPassword.view.ForgetPasswordView
import com.example.newmovlex.network.features.RequestListener
import com.example.newmovlex.network.features.UserRequest
import com.example.newmovlex.ui.models.CustomDialog
import com.example.newmovlex.ui.models.User
import com.example.newmovlex.utils.AppSharedFunctions

class ForgetPasswordPresenter(val mActivity: Activity, val mView: ForgetPasswordView) {

    @RequiresApi(Build.VERSION_CODES.M)
     fun send(
        email: EditText
    ) {
        sendEmailUser(
            AppSharedFunctions.getStringFromEditText(email)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendEmailUser(
        email: String)
    {
        if (AppSharedFunctions.networkIsConnected(mActivity)){
            val params = androidx.collection.ArrayMap<String, Any>()
            params["email"] = email

            UserRequest().forgetPassword(params, object : RequestListener<User?>{

                override fun onFail(message: String?, code: Int) {
                    mView.showErrorMsg(message)

                }

                override fun onSuccess(data: User?) {
                    Log.e("---", "onSuccess: $data", )
                }


            })
        }
    }
    fun createDialog(ed_email_forget_password: EditText) {

        if (AppSharedFunctions.checkEditTextIsEmpty(ed_email_forget_password)) {
            AppSharedFunctions.showErrorField(ed_email_forget_password, "Email address required")
            return
        }

        val dialog = CustomDialog().newInstance(
            title =  "Send email",
            msg = "Are you sure you want to use this email?",
            btnCancel = "Accept",
            btnOk = "Cancel"
        )
        dialog.show(
            (mActivity as AppCompatActivity).supportFragmentManager,
            "CustomDialogFragment"
        )
        dialog.onClickListener(object : CustomDialog.CustomDialogListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onDialogPositiveClick(str: String) {
                send(ed_email_forget_password)
                dialog.dismiss()
            }

            override fun onDialogNegativeClick(str: String) {
                dialog.dismiss()
            }

        })
    }
    }

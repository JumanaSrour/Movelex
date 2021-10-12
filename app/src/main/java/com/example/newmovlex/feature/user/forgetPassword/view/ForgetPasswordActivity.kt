package com.example.newmovlex.feature.user.forgetPassword.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.newmovlex.R
import com.example.newmovlex.feature.user.forgetPassword.presenter.ForgetPasswordPresenter
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : AppCompatActivity(), ForgetPasswordView {

    private lateinit var presenter: ForgetPasswordPresenter
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        initPresenter()
        setEventsListener()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setEventsListener() {
        btn_send.setOnClickListener {
            presenter.createDialog(ed_email_forget_password)
        }
    }

    private fun initPresenter() {
        presenter = ForgetPasswordPresenter(this, this)

    }

    override fun showErrorMsg(msg: String?) {
        Toast.makeText(applicationContext, "$msg", Toast.LENGTH_SHORT).show()
    }
}
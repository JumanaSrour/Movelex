package com.example.newmovlex.feature.user.login.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.newmovlex.R
import com.example.newmovlex.feature.movie.displayMovies.view.HomeMoviesActivity
import com.example.newmovlex.feature.user.forgetPassword.view.ForgetPasswordActivity
import com.example.newmovlex.feature.user.login.presenter.LoginPresenter
import com.example.newmovlex.feature.user.signup.view.SignupActivity
import com.example.newmovlex.ui.models.User
import com.example.newmovlex.utils.MovlexApp
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var presenter: LoginPresenter
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setEventsListener()
        initPresenter()

    }

    private fun initPresenter() {
        presenter = LoginPresenter(this, this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setEventsListener() {
        btn_signup_login.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
        btn_login_login.setOnClickListener {
            presenter.login(ed_email_login, ed_password_login)
            llProgressBar.visibility = View.VISIBLE
            Log.d("ttt", "setEventsListener: ")
        }

        btn_forget_password.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
    }

    override fun returnUser(user: User) {
        startActivity(Intent(applicationContext, HomeMoviesActivity::class.java))
        finish()
        Log.d("ttt", "returnUser: ")
    }

    override fun showErrorMsg(msg: String?) {
        Toast.makeText(applicationContext, msg.toString(), Toast.LENGTH_SHORT).show()
    }
}
package com.example.newmovlex.feature.user.signup.view

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
import com.example.newmovlex.feature.user.login.view.LoginActivity
import com.example.newmovlex.feature.user.signup.presenter.SignupPresenter
import com.example.newmovlex.ui.models.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.btn_login_login

class SignupActivity : AppCompatActivity(), SignupView {
    private lateinit var presenter: SignupPresenter
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initPresenter()
        setEventsListener()
    }

    private fun initPresenter() {
        presenter = SignupPresenter(this, this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setEventsListener() {
        btn_login_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn_register_signup.setOnClickListener {
            presenter.signup(ed_username,ed_email, ed_password_signup, ed_confirm_password_signup)
            llProgressBarSignup.visibility = View.VISIBLE
        }

    }

    override fun returnUser(user: User) {
        Log.e("----", "returnUser: $user", )
        startActivity(Intent(this, HomeMoviesActivity::class.java))
    }

    override fun showErrorMsg(msg: String?) {
        Toast.makeText(applicationContext, msg.toString(), Toast.LENGTH_SHORT).show()
    }
}
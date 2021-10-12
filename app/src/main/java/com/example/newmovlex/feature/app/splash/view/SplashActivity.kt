package com.example.newmovlex.feature.app.splash.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.newmovlex.R
import com.example.newmovlex.feature.app.onboarding.view.OnboardingActivity
import com.example.newmovlex.feature.movie.displayMovies.view.HomeMoviesActivity
import com.example.newmovlex.feature.user.login.view.LoginActivity
import com.example.newmovlex.feature.user.signup.view.SignupActivity
import com.example.newmovlex.utils.storage.SharedPrefManager
import com.example.newmovlex.utils.storage.SharedPrefManager.Companion.isFirstOpen
import com.example.newmovlex.utils.storage.SharedPrefManager.Companion.isLoggedIn
import com.example.newmovlex.utils.storage.SharedPrefManager.Companion.setFirstOpen

class SplashActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initProperties()
        setFlags()
        setHandle()

    }


    private fun checkUserLoggedIn() {
        if (isFirstOpen) {
            if (isLoggedIn) {
                startActivity(Intent(this, HomeMoviesActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        } else {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }


    private fun setHandle() {
        handler.postDelayed({
            checkUserLoggedIn()
        }, 3000)
    }


    private fun setFlags() {
        window.setFlags(
            WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW,
            WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW
        )
    }

    private fun initProperties() {
        handler = Handler()
    }
}
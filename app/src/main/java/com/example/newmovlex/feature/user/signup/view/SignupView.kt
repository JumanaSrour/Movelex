package com.example.newmovlex.feature.user.signup.view

import com.example.newmovlex.ui.models.User

interface SignupView {
    fun returnUser(user: User)
    fun showErrorMsg(msg: String?)
}
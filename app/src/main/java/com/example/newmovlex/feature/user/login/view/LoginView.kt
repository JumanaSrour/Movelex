package com.example.newmovlex.feature.user.login.view

import com.example.newmovlex.ui.models.User

interface LoginView {
    fun returnUser(user: User)
    fun showErrorMsg(msg: String?)
}
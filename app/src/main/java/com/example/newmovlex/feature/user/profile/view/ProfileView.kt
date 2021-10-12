package com.example.newmovlex.feature.user.profile.view

import com.example.newmovlex.ui.models.User

interface ProfileView {
    fun returnUser(user: User)
    fun showErrorMsg(msg: String?)
}
package com.example.newmovlex.ui.models


data class LoginResponse(
    var token: TokenBean,
    val user: User,
) {
}
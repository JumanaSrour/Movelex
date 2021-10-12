package com.example.newmovlex.ui.models

import com.google.gson.Gson


data class AppResponse(
    var status: Boolean,
    var status_code: Int,
    var message: String,
    var items: Any,

    ) {
    fun getResult(): String {
        return Gson().toJson(items).toString()
    }


}
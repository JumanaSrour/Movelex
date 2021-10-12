package com.example.newmovlex.network.features

interface PaginationListener<T> {
    fun onSuccess(data: T, num:Int)
    fun onFailure(message:String)
}
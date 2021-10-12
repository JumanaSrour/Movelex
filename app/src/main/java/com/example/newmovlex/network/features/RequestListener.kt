package com.example.newmovlex.network.features

interface RequestListener<T> {
    fun onSuccess(data: T)
    fun onFail(message: String?, code: Int)
}
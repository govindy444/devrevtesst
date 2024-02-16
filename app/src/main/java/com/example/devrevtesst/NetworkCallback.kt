package com.example.devrevtesst

interface NetworkCallback {

    fun onSuccess(response: String?)
    fun onError(errorMessage: String?)
}
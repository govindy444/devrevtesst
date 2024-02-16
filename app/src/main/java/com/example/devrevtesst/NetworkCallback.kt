package com.example.devrevcustomnetworksdk

interface NetworkCallback {

    fun onSuccess(response: String?)
    fun onError(errorMessage: String?)
}
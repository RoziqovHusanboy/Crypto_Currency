package com.example.cryptocurrency.common

sealed class Resource<T>(data:T?, massage:String? =null){
data class Success<T>(val data: T?):Resource<T>(data)
    data class Error<T>(val massage: String?,val data: T?=null):Resource<T>(data,massage)

}
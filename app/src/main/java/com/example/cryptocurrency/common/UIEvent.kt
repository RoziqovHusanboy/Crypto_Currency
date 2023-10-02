package com.example.cryptocurrency.common

sealed class UIEvent{
    data class Success<T>(val data: T?):UIEvent()
    data class Error<T>(val massage: String?):UIEvent()
    object Loading:UIEvent()
    object Empty:UIEvent()


}
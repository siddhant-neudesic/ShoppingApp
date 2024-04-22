package com.example.shoppingapp.models

sealed class Result<out T : Any> {
    class Success<out T: Any>(val data: T) : Result<T>()
    class Error(val exception: String?) : Result<Nothing>()


    override fun toString(): String {
        return when(this){
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
package com.example.rewasteappmd.network.response

data class BaseResponse<T> (

    val success: Boolean,

    val message: String,

    val data: T,

)
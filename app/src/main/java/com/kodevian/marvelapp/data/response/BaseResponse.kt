package com.kodevian.marvelapp.data.response




data class BaseResponse<out T> (val code:Int, val status: String, val etag:String, val data: DataResponse<T>)
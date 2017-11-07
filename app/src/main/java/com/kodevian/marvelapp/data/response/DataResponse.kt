package com.kodevian.marvelapp.data.response


data class DataResponse<out T>(val offset:Int, val limit:Int, val total:Int, val count:Int, val results:List<T>)
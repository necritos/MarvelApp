package com.kodevian.marvelapp.model



data class Url(val type:String, val url:String)

data class Thumbnail(val path:String, val extension:String){
    fun getFullImage():String{
        return "$path.$extension"
    }
}
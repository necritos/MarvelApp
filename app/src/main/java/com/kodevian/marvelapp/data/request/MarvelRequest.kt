package com.kodevian.marvelapp.data.request

import com.kodevian.marvelapp.data.response.BaseResponse
import com.kodevian.marvelapp.model.CharacterEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap



interface MarvelRequest {


    @GET("characters")
    fun getCharacters(
                      @Query("apikey") apikey: String,
                      @Query("ts") ts: String,
                      @Query("hash") hash: String,
                      @QueryMap options:Map<String,String>
                      ): Observable<BaseResponse<CharacterEntity>>

    enum class OrderBy(val value:String){
        NAME("name"), MODIFIED("modified")
    }
}


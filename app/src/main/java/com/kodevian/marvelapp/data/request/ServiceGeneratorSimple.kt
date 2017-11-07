package com.kodevian.marvelapp.data.request


import com.kodevian.marvelapp.BuildConfig

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



object ServiceGeneratorSimple {

    val API_BASE_URL = BuildConfig.BASE

    //private val httpClient = OkHttpClient()
    private val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())




    /*fun <S> createService(serviceClass: Class<S>): S {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        val retrofit = builder.client(httpClient).client(client).build()
        return retrofit.create(serviceClass)
    }*/

    fun <S> createServiceRx(serviceClass: Class<S>): S {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val retrofit = builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(serviceClass)
    }
}

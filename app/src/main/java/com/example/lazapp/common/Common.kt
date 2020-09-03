package com.example.lazapp.common

import com.example.lazapp.retrofitclient.RetrofitClientInstance
import retrofit2.Retrofit

object Common {
        private const val BASE_URL = "http://taluan.webstarterz.com"
    val retrofitService: Retrofit
        get() = RetrofitClientInstance.getClient(BASE_URL)
}
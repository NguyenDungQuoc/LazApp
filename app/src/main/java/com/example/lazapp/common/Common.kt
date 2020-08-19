package com.example.lazapp.common

import com.example.lazapp.retrofitclient.RetrofitClientInstance
import retrofit2.Retrofit

object Common {
    private const val BASE_URL = "https://b36ee900-14d6-4627-a59f-5caa804450e9.mock.pstmn.io"
    val retrofitService: Retrofit
        get() = RetrofitClientInstance.getClient(BASE_URL)
}
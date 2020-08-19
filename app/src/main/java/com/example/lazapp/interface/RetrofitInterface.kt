package com.example.lazapp.`interface`

import com.example.lazapp.model.ProductModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {
    /**
     * @GET declares an HTTP GET request
     */
    @GET("/getAllHomeData")
    fun getAllHomeData(): Call<ProductModel>
}
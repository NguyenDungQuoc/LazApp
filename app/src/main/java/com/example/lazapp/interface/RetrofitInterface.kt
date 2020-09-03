package com.example.lazapp.`interface`

import com.example.lazapp.model.ProductModel
import com.example.lazapp.model.ProductResult
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {
    /**
     * @GET declares an HTTP GET request
     */
    @GET("/App/TestApi/home.json")
    fun getAllHomeData(): Call<ProductModel<ProductResult>>
}
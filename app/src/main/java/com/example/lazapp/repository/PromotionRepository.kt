package com.example.lazapp.repository


import com.example.lazapp.`interface`.RetrofitInterface
import com.example.lazapp.model.ProductModel
import com.example.lazapp.common.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromotionRepository {
    private val retrofitService: RetrofitInterface =
        Common.retrofitService.create(RetrofitInterface::class.java)

    fun getAllHomeData(callback: (ProductModel?) -> (Unit)) {
        retrofitService.getAllHomeData().enqueue(object : Callback<ProductModel> {
            override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                callback.invoke(response.body())
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {

            }
        })

    }
}
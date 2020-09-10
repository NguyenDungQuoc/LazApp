package com.example.lazapp.repository


import com.example.lazapp.`interface`.RetrofitInterface
import com.example.lazapp.model.BaseResponse
import com.example.lazapp.common.Common
import com.example.lazapp.model.ProductResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromotionRepository {
    private val retrofitService: RetrofitInterface =
        Common.retrofitService.create(RetrofitInterface::class.java)

    fun getAllHomeData(callback: (BaseResponse<ProductResult>?) -> (Unit), callbackError: (String?) ->(Unit)) {
        retrofitService.getAllHomeData().enqueue(object : Callback<BaseResponse<ProductResult>> {
            override fun onResponse(call: Call<BaseResponse<ProductResult>>, response: Response<BaseResponse<ProductResult>>) {
                callback.invoke(response.body())
            }

            override fun onFailure(call: Call<BaseResponse<ProductResult>>, t: Throwable) {
                callbackError.invoke("error")
            }
        })

    }
}
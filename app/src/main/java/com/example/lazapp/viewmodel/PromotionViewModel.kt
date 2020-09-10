package com.example.lazapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lazapp.model.BaseResponse
import com.example.lazapp.model.ProductResult
import com.example.lazapp.repository.PromotionRepository

class PromotionViewModel : ViewModel() {
    private var promotionRepository: PromotionRepository? = PromotionRepository()
    var result: MutableLiveData<BaseResponse<ProductResult>> = MutableLiveData<BaseResponse<ProductResult>>()
    var errorMessage: MutableLiveData<String>? = MutableLiveData()

    fun getAllHomeData() {
        promotionRepository?.getAllHomeData({
            result.value = it
        }, {
            errorMessage?.value = it
        })
    }
}
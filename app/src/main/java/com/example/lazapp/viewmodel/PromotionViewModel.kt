package com.example.lazapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lazapp.model.ProductModel
import com.example.lazapp.repository.PromotionRepository

class PromotionViewModel() : ViewModel() {
    var promotionRepository: PromotionRepository? = PromotionRepository()
    var result: MutableLiveData<ProductModel> = MutableLiveData<ProductModel>()

    fun getAllHomeData() {
        promotionRepository?.getAllHomeData {
            result.value = it
        }
    }
}
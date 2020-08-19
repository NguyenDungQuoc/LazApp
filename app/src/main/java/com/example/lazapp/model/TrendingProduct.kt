package com.example.lazapp.model

import com.example.lazapp.model.ItemProduct

data class TrendingProduct(
    var entityType: String? = null,
    var popularName: String? = null,
    var id: String? = null,
    var itemCnt: String? = null,
    var itemWantCnt: String? = null,
    var popularSubName: String? = null,
    var items: MutableList<ItemProduct>
)
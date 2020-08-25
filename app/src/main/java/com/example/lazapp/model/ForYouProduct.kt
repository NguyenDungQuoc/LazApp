package com.example.lazapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ForYouProduct(
    var itemRatingScore: Float? = null,
    var brandId: Int? = null,
    var currency: String? = null,//7
    var id: String? = null,//8
    var shopId: Int? = null,
    var itemImg: String? = null,//4
    var skuId: String? = null,
    var itemTitle: String? = null,//1
    var categoryId: Int? = null,
    var itemDiscountPrice: String? = null,//5
    var itemPrice: String? = null,//6
    var itemId: Int? = null,//3
    var itemDiscount: String? = null,//2
    var itemUrl: String? = null,//9


) : Parcelable
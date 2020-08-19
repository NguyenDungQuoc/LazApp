package com.example.lazapp.model


class ProductResult(
    var promotion: MutableList<PromotionProduct>,
    var flashSale: MutableList<FlashSaleProduct>,
    var trending: MutableList<TrendingProduct>,
    var forYou: MutableList<ForYouProduct>
)
package com.liamfarrell.android.koganclone.model.trendingproducts

import com.liamfarrell.android.koganclone.model.Product

data class TrendingProductPageApiDeserializerPOJO(
    val products: List<Product>,
    val nextPage: Int?
)
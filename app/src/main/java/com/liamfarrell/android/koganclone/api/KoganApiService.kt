package com.liamfarrell.android.koganclone.api

import com.liamfarrell.android.koganclone.model.Product
import com.liamfarrell.android.koganclone.model.delivery.DeliveryCostApiDeserializerPOJO
import com.liamfarrell.android.koganclone.model.delivery.FreightProtectionCostApiDeserializerPOJO
import com.liamfarrell.android.koganclone.model.notification.NotificationCountApiDeserializerPOJO
import com.liamfarrell.android.koganclone.model.notification.NotificationListApiDeserializerPOJO
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductPageApiDeserializerPOJO
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductUpdateCount
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 *  REST API access points
 */


interface KoganApiService {
    @GET("product/{productid}")
    fun getProduct(@Path("productid") productId: Int): Call<Product>

    @GET("notification")
    fun getNotificationList() : Call<NotificationListApiDeserializerPOJO>

    @GET("notification/count")
    fun getNotificationCount(): Call<NotificationCountApiDeserializerPOJO>

    @GET("product/trending/updatecount")
    fun getTrendingProductUpdateCount(): Call<TrendingProductUpdateCount>

    @GET("product/trending/{pageid}")
    fun getTrendingProductList(@Path("pageid") pageid: Int) : Call<TrendingProductPageApiDeserializerPOJO>

    @POST("delivery/cost")
    fun getDeliveryPrice(@Body productIdList: List<Int>): Call<DeliveryCostApiDeserializerPOJO>

    @POST("delivery/freightprotection")
    fun getFreightProtectionPrice(@Body productIdList: List<Int>): Call<FreightProtectionCostApiDeserializerPOJO>


}
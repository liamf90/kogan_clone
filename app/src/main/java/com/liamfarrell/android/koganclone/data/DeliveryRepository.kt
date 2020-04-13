package com.liamfarrell.android.koganclone.data

import com.liamfarrell.android.koganclone.api.KoganApiService
import com.liamfarrell.android.koganclone.model.AsyncTaskResult
import com.liamfarrell.android.koganclone.model.Product
import com.liamfarrell.android.koganclone.model.delivery.DeliveryCostApiDeserializerPOJO
import com.liamfarrell.android.koganclone.model.delivery.FreightProtectionCostApiDeserializerPOJO
import com.liamfarrell.android.koganclone.util.executeRestApiFunction
import javax.inject.Inject

class DeliveryRepository @Inject constructor(
    private val koganApiService: KoganApiService
) {
    suspend fun getDeliveryPrice(items: List<Product>, isCustomerKoganFirstCustomer: Boolean) : AsyncTaskResult<DeliveryCostApiDeserializerPOJO?> {

            val allItemsList = if (isCustomerKoganFirstCustomer){
               items.filter { !it.isKoganFirstEligible && !it.freeShipping }.map{ it.productId }
            } else {
                items.map{ it.productId }
            }

            if (allItemsList.isNotEmpty()){
                return executeRestApiFunction( koganApiService.getDeliveryPrice(allItemsList))
            } else {
                return AsyncTaskResult(DeliveryCostApiDeserializerPOJO(0.00))
            }
    }


    suspend fun getFreightProtectionPrice(items: List<Product>) : AsyncTaskResult<FreightProtectionCostApiDeserializerPOJO?> {
        val allItemsList = items.map { it.productId }
        return executeRestApiFunction( koganApiService.getFreightProtectionPrice(allItemsList))
    }
}
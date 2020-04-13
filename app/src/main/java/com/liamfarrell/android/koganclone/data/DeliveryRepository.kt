package com.liamfarrell.android.koganclone.data

import com.liamfarrell.android.koganclone.api.KoganApiService
import com.liamfarrell.android.koganclone.db.ShoppingCartDao
import com.liamfarrell.android.koganclone.model.ApiException
import com.liamfarrell.android.koganclone.model.Product
import com.liamfarrell.android.koganclone.model.delivery.DeliveryPriceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeliveryRepository @Inject constructor(
    private val koganApiService: KoganApiService
) {
    suspend fun getDeliveryPrice(items: List<Product>, isCustomerKoganFirstCustomer: Boolean) : DeliveryPriceResult {
        return withContext(Dispatchers.IO) {
            val allItemsList = if (isCustomerKoganFirstCustomer){
               items.filter { !it.isKoganFirstEligible && !it.freeShipping }.map{ it.productId }
            } else {
                items.map{ it.productId }
            }

            if (allItemsList.isNotEmpty()){
                val deliveryPriceCall = koganApiService.getDeliveryPrice(allItemsList).execute()
                if (deliveryPriceCall.isSuccessful){
                    return@withContext DeliveryPriceResult(deliveryPriceCall.body()?.delivery_cost, null)
                } else {
                    return@withContext(DeliveryPriceResult(null, ApiException(deliveryPriceCall.code())))
                }

            } else {
                return@withContext DeliveryPriceResult(0.00, null)
            }


        }
    }

    suspend fun getFreightProtectionPrice(items: List<Product>) : DeliveryPriceResult {
        return withContext(Dispatchers.IO) {
            val allItemsList = items.map { it.productId }

            val freightProtectionCall = koganApiService.getFreightProtectionPrice(allItemsList).execute()
            freightProtectionCall.errorBody()
            if (freightProtectionCall.isSuccessful){
                return@withContext DeliveryPriceResult(freightProtectionCall.body()?.freight_protection_cost, null)
            } else {
                return@withContext(DeliveryPriceResult(null, ApiException(freightProtectionCall.code())))
            }
        }
    }
}
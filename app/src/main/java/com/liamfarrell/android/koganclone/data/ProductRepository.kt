package com.liamfarrell.android.koganclone.data

import com.liamfarrell.android.koganclone.api.KoganApiService
import com.liamfarrell.android.koganclone.model.AsyncTaskResult
import com.liamfarrell.android.koganclone.model.Product
import com.liamfarrell.android.koganclone.util.executeRestApiFunction
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val koganApiService: KoganApiService)
{

    suspend fun getProduct(productId: Int) : AsyncTaskResult<Product?> {
        return executeRestApiFunction( koganApiService.getProduct(productId))
    }
}
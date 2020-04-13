package com.liamfarrell.android.koganclone.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.liamfarrell.android.koganclone.api.KoganApiService
import com.liamfarrell.android.koganclone.db.TrendingProductDao
import com.liamfarrell.android.koganclone.db.TrendingProductDatabaseResult
import com.liamfarrell.android.koganclone.db.TrendingProductsBoundaryCallback
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductDb
import com.liamfarrell.android.koganclone.util.executeRestApiFunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingProductRepository @Inject constructor(
    private val koganApiService: KoganApiService,
    private val trendingProductDao: TrendingProductDao
)
{
    private val _networkErrors = MutableLiveData<Exception>()

    private val _spinner = MutableLiveData<Boolean>()
    val spinner : LiveData<Boolean> = _spinner

    // LiveData of network errors.
    val networkErrors: LiveData<Exception>
        get() = _networkErrors



    fun loadAllTrendingProducts(coroutineScope: CoroutineScope) : TrendingProductDatabaseResult {

        // Get data source factory from the local cache
        val dataSourceFactory = trendingProductDao.getAllTrendingProducts()


        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data

        //val boundaryCallback = TrendingProductsBoundaryCallback (trendingProductsManager,coroutineScope)
        val boundaryCallback =
            TrendingProductsBoundaryCallback(
                coroutineScope,
                koganApiService,
                trendingProductDao,
                _networkErrors
            )

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return TrendingProductDatabaseResult(
            data,
            networkErrors
        )
    }


    /**
     * Checks if the trending product list has been updated. If it has, deletes the list from the Room Database and re inserts it
     */
    suspend fun checkForUpdates(){
        withContext(Dispatchers.IO) {
            //get notification count from server
            val trendingProductsCountResponse = executeRestApiFunction(koganApiService.getTrendingProductUpdateCount())
            if (trendingProductsCountResponse.error == null) {
                val trendingProductsUpdateCountServer = trendingProductsCountResponse.result?.trending_products_update_count
                val trendingProductsUpdateCountDevice = trendingProductDao.getTrendingProductCount()

                //if notification count on server does not equal the notifications count on device database,
                //then update the notifications list from the server
                if (trendingProductsUpdateCountServer != trendingProductsUpdateCountDevice) {
                    _spinner.postValue(true)
                    val trendingProductsToAddRequest = executeRestApiFunction(koganApiService.getTrendingProductList(1))
                    if (trendingProductsToAddRequest.error == null) {
                        val trendingProductsApiCallBody = trendingProductsToAddRequest.result
                        val trendingProductsToAdd = trendingProductsApiCallBody?.products
                        val nextPage = trendingProductsApiCallBody?.nextPage
                        val trendingProductsDbList = trendingProductsToAdd?.map {product ->
                            TrendingProductDb(
                                product
                            )
                        }
                        trendingProductsDbList?.let { trendingProductsUpdateCountServer?.let{trendingProductDao.newTrendingProductList( trendingProductsDbList, trendingProductsUpdateCountServer,nextPage) }}
                        _spinner.postValue(false)
                    } else {
                        _spinner.postValue(false)
                        _networkErrors.postValue(trendingProductsToAddRequest.error)
                    }
                }
            } else {
                _spinner.postValue( false)
                _networkErrors.postValue(trendingProductsCountResponse.error)
            }
        }
    }


    fun deleteTrendingProducts(coroutineScope: CoroutineScope){
        coroutineScope.launch(Dispatchers.IO) {
            trendingProductDao.deleteTrendingProducts()
        }
    }



    companion object {
        private const val DATABASE_PAGE_SIZE = 10
    }
}
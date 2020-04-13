package com.liamfarrell.android.koganclone.db

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.liamfarrell.android.koganclone.api.KoganApiService
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductDb
import com.liamfarrell.android.koganclone.util.executeRestApiFunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * This boundary callback gets notified when user reaches to the edges of the list for example when
 * the database cannot provide any more data.
 **/
class TrendingProductsBoundaryCallback(
    private val coroutineScope: CoroutineScope,
    private val koganApiService: KoganApiService,
    private val trendingProductDao: TrendingProductDao,
    private val networkErrors : MutableLiveData<Exception>
) : PagedList.BoundaryCallback<TrendingProductDb>() {


    override fun onZeroItemsLoaded() {
        Timber.d("onZeroItemsLoaded")
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: TrendingProductDb) {
        coroutineScope.launch (Dispatchers.IO){
            //Next page stored in trending product dao
            val nextPage = trendingProductDao.getTrendingProductNextPage()
            // Get next page from db
            if (nextPage != null) {
                //Get next page from koganServiceApi
                val nextPageProductListResult = executeRestApiFunction(koganApiService.getTrendingProductList(nextPage))
                if (nextPageProductListResult.error == null){
                    val productDbList = nextPageProductListResult.result?.products?.map {
                        TrendingProductDb(
                            it
                        )
                    }

                    val nextPageUpdate = nextPageProductListResult.result?.nextPage
                    //update trending product dao with new values (nextPage + List<trendingProductDb>
                    productDbList?.let {trendingProductDao.insertNewPage(productDbList, nextPageUpdate) }
                } else {
                    networkErrors.postValue(nextPageProductListResult.error)
                }
            }
            else {
                //no more items
            }
        }
    }

}
package com.liamfarrell.android.koganclone.ui.home

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.liamfarrell.android.koganclone.data.TrendingProductRepository
import com.liamfarrell.android.koganclone.db.TrendingProductDatabaseResult
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel used in [HomeFragment].
 */
class HomeViewModel @Inject constructor(val trendingProductRepository: TrendingProductRepository): ViewModel() {

    private val trendingProductsResult = MutableLiveData<TrendingProductDatabaseResult>()
    val notifications: LiveData<PagedList<TrendingProductDb>> = Transformations.switchMap(trendingProductsResult) { it.data }
    val networkErrors: LiveData<Exception> = Transformations.switchMap(trendingProductsResult) { it -> it.networkErrors }

    private val _spinner = MediatorLiveData<Boolean>()
    val spinner : LiveData<Boolean> = _spinner



    init {
        _spinner.addSource(trendingProductRepository.spinner){newValue -> _spinner.value =  newValue}
        trendingProductsResult.value = trendingProductRepository.loadAllTrendingProducts(viewModelScope)
        viewModelScope.launch {
            trendingProductRepository.checkForUpdates()
        }
    }



    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
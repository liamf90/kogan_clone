package com.liamfarrell.android.koganclone.db

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductDb

/**
 * TrendingProductDatabaseResult from a load database, which contains LiveData<PagedList<TrendingProductDB>> holding query data,
 * and a LiveData<String> of network error state.
 */
data class TrendingProductDatabaseResult(
    val data: LiveData<PagedList<TrendingProductDb>>,
    val networkErrors: LiveData<String>
)

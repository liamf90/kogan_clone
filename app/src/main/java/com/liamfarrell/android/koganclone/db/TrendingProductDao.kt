package com.liamfarrell.android.koganclone.db

import androidx.paging.DataSource
import androidx.room.*
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductDb
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductUpdateCount


/**
 * The Data Access Object for the [TrendingProductDb] and [TrendingProductUpdateCount] class.
 */
@Dao
interface TrendingProductDao {

    @Query("SELECT * FROM trending_product ORDER BY position_index")
    fun getAllTrendingProducts(): DataSource.Factory<Int, TrendingProductDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<TrendingProductDb>)

    @Transaction
    suspend fun insertNewPage(products: List<TrendingProductDb>, nextPage: Int?){
        insertAll(products)
        updateTrendingProductNextPage(nextPage)
    }

    @Transaction
    suspend fun newTrendingProductList(products: List<TrendingProductDb>, newTrendingProductUpdateCount: Int, nextPage: Int?){
        deleteTrendingProducts()
        insertTrendingProductCount(
            TrendingProductUpdateCount(
                newTrendingProductUpdateCount,
                nextPage
            )
        )
        insertAll(products)
    }

    @Query("SELECT trending_products_update_count FROM trending_products_update_count")
    suspend fun getTrendingProductCount(): Int?

    @Query("SELECT next_page FROM trending_products_update_count")
    suspend fun getTrendingProductNextPage(): Int?

    @Query("UPDATE trending_products_update_count SET trending_products_update_count = :newUpdateCount, next_page = :nextPage")
    suspend fun updateTrendingProductCount(newUpdateCount: Int, nextPage: Int?): Int

    @Query("UPDATE trending_products_update_count SET next_page = :nextPage")
    suspend fun updateTrendingProductNextPage(nextPage: Int?): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingProductCount(trendingProductUpdateCount: TrendingProductUpdateCount)


    @Query("DELETE FROM trending_product")
    suspend fun deleteTrendingProducts()
}
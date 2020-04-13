package com.liamfarrell.android.koganclone.model.trendingproducts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
This class 2 values.
1. `trending_product_update_count` an Int indicating the amount of times the trending product list has been updated on the server.
This value can be compared with current update count on the server to see if the list needs updating
2. `next_page` an Int indicating the next page for the next list of trending products that have not yet been put in the database.

 */
@Entity(tableName = "trending_products_update_count")
class  TrendingProductUpdateCount(val trending_products_update_count: Int, val next_page: Int?) {
    //Since we only want 1 row to store the trending_product_update_count, the primary key id field can only be set to 1
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id = 1
        set(_) {field = 1}
}

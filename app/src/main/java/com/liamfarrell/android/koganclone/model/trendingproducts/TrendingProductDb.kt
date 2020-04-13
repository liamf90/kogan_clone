package com.liamfarrell.android.koganclone.model.trendingproducts

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.liamfarrell.android.koganclone.model.Product

@Entity(tableName = "trending_product")
class TrendingProductDb (
    @Embedded val product : Product
) {
    @PrimaryKey(autoGenerate = true) var position_index: Int = 0
}
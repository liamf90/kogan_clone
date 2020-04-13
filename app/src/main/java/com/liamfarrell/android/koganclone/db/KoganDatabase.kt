package com.liamfarrell.android.koganclone.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.liamfarrell.android.koganclone.model.notification.Notification
import com.liamfarrell.android.koganclone.model.shoppingcart.ShoppingCartOrderItem
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductDb
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductUpdateCount

/**
 * The Room database for this app
 */
@Database(entities = [Notification::class, ShoppingCartOrderItem::class, TrendingProductDb::class, TrendingProductUpdateCount::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class KoganDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
    abstract fun shoppingCartDao(): ShoppingCartDao
    abstract fun trendingProductDao(): TrendingProductDao
}
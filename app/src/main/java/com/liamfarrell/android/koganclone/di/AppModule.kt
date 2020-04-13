package com.liamfarrell.android.koganclone.di

import android.app.Application
import androidx.room.Room
import com.liamfarrell.android.koganclone.db.KoganDatabase
import com.liamfarrell.android.koganclone.db.NotificationDao
import com.liamfarrell.android.koganclone.db.ShoppingCartDao
import com.liamfarrell.android.koganclone.db.TrendingProductDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): KoganDatabase {
        return Room
            .databaseBuilder(app, KoganDatabase::class.java, "koganclone.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNotificationDao(db: KoganDatabase): NotificationDao {
        return db.notificationDao()
    }

    @Singleton
    @Provides
    fun provideShoppingCartDao(db: KoganDatabase): ShoppingCartDao {
        return db.shoppingCartDao()
    }

    @Singleton
    @Provides
    fun provideTrendingProductDao(db: KoganDatabase): TrendingProductDao {
        return db.trendingProductDao()
    }


}

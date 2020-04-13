package com.liamfarrell.android.koganclone.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.liamfarrell.android.koganclone.model.notification.Notification

/**
 * The Data Access Object for the [Notification] class.
 */
@Dao
interface NotificationDao {

    @Query("SELECT * FROM notification ORDER BY id DESC")
    fun getAllNotifications(): DataSource.Factory<Int, Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<Notification>)

    @Query("SELECT COUNT(*) FROM notification")
    suspend fun getCountAllNotifications(): Int

    @Query("DELETE FROM notification")
    suspend fun deleteNotifications()
}
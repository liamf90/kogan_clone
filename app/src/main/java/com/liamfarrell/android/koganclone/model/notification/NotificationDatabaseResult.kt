package com.liamfarrell.android.koganclone.model.notification

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * NotificationsDatabaseResult from a load database, which contains LiveData<PagedList<Notification>> holding query data,
 * and a LiveData<String> of network error state.
 */
data class NotificationsDatabaseResult(
    val data: LiveData<PagedList<Notification>>,
    val networkErrors: LiveData<String>
)

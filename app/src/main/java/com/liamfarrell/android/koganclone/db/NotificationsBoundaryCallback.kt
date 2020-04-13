package com.liamfarrell.android.koganclone.db

import androidx.paging.PagedList
import com.liamfarrell.android.koganclone.model.notification.Notification
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

/**
 * This boundary callback gets notified when user reaches to the edges of the list for example when
 * the database cannot provide any more data.
 **/
class NotificationsBoundaryCallback(
    private val coroutineScope: CoroutineScope
) : PagedList.BoundaryCallback<Notification>() {


    override fun onZeroItemsLoaded() {
        Timber.d("onZeroItemsLoaded")
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Notification) {
        // Future Implementation - API Paging
    }

}
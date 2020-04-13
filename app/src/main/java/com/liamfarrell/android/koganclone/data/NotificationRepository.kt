package com.liamfarrell.android.koganclone.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import kotlinx.coroutines.withContext
import com.liamfarrell.android.koganclone.api.KoganApiService
import com.liamfarrell.android.koganclone.db.NotificationDao
import com.liamfarrell.android.koganclone.db.NotificationsBoundaryCallback
import com.liamfarrell.android.koganclone.model.notification.NotificationsDatabaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val koganApiService: KoganApiService,
    private val notificationsDao: NotificationDao)
{
    private val _networkErrors = MutableLiveData<String>()

    private val _spinner = MutableLiveData<Boolean>()
    val spinner : LiveData<Boolean> = _spinner

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors



    fun loadAllNotifications(coroutineScope: CoroutineScope) : NotificationsDatabaseResult {

        // Get data source factory from the local cache
        val dataSourceFactory = notificationsDao.getAllNotifications()


        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data

        //val boundaryCallback = NotificationsBoundaryCallback (notificationsManager,coroutineScope)
        val boundaryCallback = NotificationsBoundaryCallback (coroutineScope)

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return NotificationsDatabaseResult(
            data,
            networkErrors
        )
    }

    suspend fun checkForUpdates(){
        withContext(Dispatchers.IO) {
            //get notification count from server
            val notificationCountResponse = koganApiService.getNotificationCount().execute()
            if (notificationCountResponse.isSuccessful) {
                val notificationCountServer = notificationCountResponse.body()?.notification_count
                val notificationCountDevice = notificationsDao.getCountAllNotifications()

                //if notification count on server does not equal the notifications count on device database,
                //then update the notifications list from the server
                if (notificationCountServer != notificationCountDevice) {
                    _spinner.postValue(true)
                    val notificationsToAddRequest = koganApiService.getNotificationList().execute()
                    if (notificationsToAddRequest.isSuccessful) {
                        val notificationsToAdd = notificationsToAddRequest.body()?.notifications
                        notificationsToAdd?.let { notificationsDao.insertAll(notificationsToAdd) }
                        _spinner.postValue(false)
                    } else {
                        _spinner.postValue(false)
                        _networkErrors.postValue("API Connection Error")
                    }
                }
            } else {
                _spinner.postValue( false)
                _networkErrors.postValue("API Connection Error")
            }
        }
    }


    fun deleteNotifications(coroutineScope: CoroutineScope){
        coroutineScope.launch(Dispatchers.IO) {
            notificationsDao.deleteNotifications()
        }
    }


    companion object {
        private const val DATABASE_PAGE_SIZE = 15
    }
}
package com.liamfarrell.android.koganclone.ui.notifications

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.liamfarrell.android.koganclone.data.NotificationRepository
import com.liamfarrell.android.koganclone.model.notification.Notification
import com.liamfarrell.android.koganclone.model.notification.NotificationsDatabaseResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel used in [NotificationsFragment].
 */
class NotificationsViewModel @Inject constructor(private val notificationsRepository: NotificationRepository) : ViewModel() {

    private val notificationsResult = MutableLiveData<NotificationsDatabaseResult>()
    val notifications: LiveData<PagedList<Notification>> = Transformations.switchMap(notificationsResult) { it.data }
    val networkErrors: LiveData<Exception> = Transformations.switchMap(notificationsResult) { it -> it.networkErrors }

    private val _spinner = MediatorLiveData<Boolean>()
    val spinner : LiveData<Boolean> = _spinner



    init {
        _spinner.addSource(notificationsRepository.spinner){newValue -> _spinner.value =  newValue}
        notificationsResult.value = notificationsRepository.loadAllNotifications(viewModelScope)
        viewModelScope.launch {
            notificationsRepository.checkForUpdates()
        }
    }


    fun checkForUpdates(){
        viewModelScope.launch {
            _spinner.value = true
            notificationsRepository.checkForUpdates()
            _spinner.value = false
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}



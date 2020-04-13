package com.liamfarrell.android.koganclone.model.notification

data class NotificationListApiDeserializerPOJO(
    val notification_count : Int,
    val notifications : List<Notification>
)
package com.liamfarrell.android.koganclone.model.notification

import android.text.format.DateUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "notification")
class Notification (
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    val title: String,
    val description: String,
    @SerializedName("timestamp") val date: Calendar,
    val destination: String?
)
{
    fun getRelativeTimeSinceNow(): String{
        return DateUtils.getRelativeTimeSpanString(date.timeInMillis, Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS).toString()
    }
}
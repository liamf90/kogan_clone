package com.liamfarrell.android.koganclone.model

import android.content.Context
import com.liamfarrell.android.koganclone.R
import java.net.UnknownHostException


fun getErrorMessage(context: Context, error: Exception): String {
    return when (error) {
        is UnknownHostException -> context.getString(R.string.connection_error)
        is CustomError -> error.getErrorToastMessage(context)
        else -> context.getString(R.string.unknown)
    }
}


abstract class CustomError() : Exception(){
    abstract fun getErrorToastMessage(context: Context) : String
}


class ApiException(val code: Int) : CustomError(){
    override fun getErrorToastMessage(context: Context): String {
        return context.getString(R.string.api_error, code)
    }
}

package com.liamfarrell.android.koganclone.model

import android.content.Context
import java.net.UnknownHostException


fun getErrorMessage(context: Context, error: Exception): String {
    return when (error) {
        is UnknownHostException -> "No Internet Connection"
        is CustomError -> error.getErrorToastMessage(context)
        else -> "An unknown error has occurred"//context.getString(R.string.server_error_toast)
    }
}


abstract class CustomError() : Exception(){
    abstract fun getErrorToastMessage(context: Context) : String
}

class InternalSqlException : CustomError(){
    override fun getErrorToastMessage(context: Context): String {
        return "An internal database error has occurred"
        //return context.getString("")
    }
}

class ApiException(val code: Int) : CustomError(){
    override fun getErrorToastMessage(context: Context): String {
        return "An API error has occurred. Code: $code"
    }
}

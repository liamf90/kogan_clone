package com.liamfarrell.android.koganclone.util

import android.view.View
import android.view.animation.AnimationUtils
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.model.ApiException
import com.liamfarrell.android.koganclone.model.AsyncTaskResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import timber.log.Timber
import java.net.UnknownHostException

fun View.zoomInAnimation(){
    val zoomInAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    startAnimation(zoomInAnimation)
}

suspend fun <R> executeRestApiFunction(sbServiceCall: Call<R>) : AsyncTaskResult<R?> {
    return withContext(Dispatchers.IO) {

        try{
            val response = sbServiceCall.execute()
            if (response.isSuccessful){
                return@withContext AsyncTaskResult<R?>(response.body())
            } else{
                return@withContext AsyncTaskResult<R?>(ApiException(response.code()))
            }
        }
        catch (uhe : UnknownHostException){
            Timber.i(uhe)
            return@withContext AsyncTaskResult<R?>(uhe)
        }
    }
}

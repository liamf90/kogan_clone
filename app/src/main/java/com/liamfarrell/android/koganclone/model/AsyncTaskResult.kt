package com.liamfarrell.android.koganclone.model

/**
 * Holds either the error or the response from an API call
 *
 * @param <T> The Response object from the API call
 */

class AsyncTaskResult<T> {
    var result: T? = null
    var error: Exception? = null

    constructor(result: T) : super() {
        this.result = result
    }

    constructor(error: Exception?) : super() {
        this.error = error
    }
}
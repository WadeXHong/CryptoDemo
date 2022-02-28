package com.bardxhong.crypto.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

internal suspend fun <T> toResult(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Response<T>,
): (suspend () -> Result<T>) {
    return suspend {
        withContext(dispatcher) {
            runCatching {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    val body = response.body()
                    checkNotNull(body)
                    body
                } else {
                    throw HttpException(response)
                }
            }
        }
    }
}
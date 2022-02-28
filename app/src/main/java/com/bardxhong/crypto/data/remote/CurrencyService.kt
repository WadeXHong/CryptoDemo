package com.bardxhong.crypto.data.remote

import com.bardxhong.crypto.data.CurrencyInfoRemoteEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * It's just created for emulating the real cases that we have remote and local source to provide
 * currency data.
 */
interface CurrencyService {
    @GET(NetworkInfo.PATH_CURRENCY_INFO)
    suspend fun getCurrencyInfoList(
        @Query("name") name: String?,
        @Query("page") page: Int?
    ): Response<List<CurrencyInfoRemoteEntity>>
}
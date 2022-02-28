package com.bardxhong.crypto.shared

import com.bardxhong.crypto.data.remote.CurrencyService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitProvider {
    private val retrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://localhost")
            .addConverterFactory(
                MoshiConverterFactory.create(MoshiUtil.moshi)
            )
            .build()
    }

    val service by lazy { retrofit.create(CurrencyService::class.java) }
}
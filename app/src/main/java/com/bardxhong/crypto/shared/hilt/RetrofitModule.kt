package com.bardxhong.crypto.shared.hilt

import android.app.Application
import com.bardxhong.crypto.BuildConfig
import com.bardxhong.crypto.data.remote.CurrencyService
import com.bardxhong.crypto.shared.FakeResponseInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideCurrencyService(
        moshi: Moshi,
        context: Application
    ): CurrencyService =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            addInterceptor(FakeResponseInterceptor(context))
                        }
                    }
                    .build()
            )
            .baseUrl("https://localhost")
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .build()
            .create(CurrencyService::class.java)

}
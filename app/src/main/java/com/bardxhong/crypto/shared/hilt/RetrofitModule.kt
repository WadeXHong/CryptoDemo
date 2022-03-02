package com.bardxhong.crypto.shared.hilt

import com.bardxhong.crypto.data.remote.CurrencyService
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
    fun provideRetrofitModule(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://localhost")
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyService(
        retrofit: Retrofit
    ): CurrencyService =
        retrofit.create(CurrencyService::class.java)

}
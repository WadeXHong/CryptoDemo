package com.bardxhong.crypto.shared.hilt

import android.app.Application
import com.bardxhong.crypto.shared.FakeResponseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object OkhttpModule {

    /**
     * Do something setting like timeout value in here.
     */
    @Provides
    @Singleton
    fun provideOkhttpModule(
        context: Application
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(FakeResponseInterceptor(context))
            .build()
    }
}
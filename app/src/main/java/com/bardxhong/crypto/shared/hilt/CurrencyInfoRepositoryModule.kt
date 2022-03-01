package com.bardxhong.crypto.shared.hilt

import com.bardxhong.crypto.data.repo.CurrencyInfoRepositoryImpl
import com.bardxhong.crypto.domain.repo.ICurrencyInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CurrencyInfoRepositoryModule {
    @Binds
    abstract fun bindICurrencyInfoRepository(
        impl: CurrencyInfoRepositoryImpl
    ): ICurrencyInfoRepository
}
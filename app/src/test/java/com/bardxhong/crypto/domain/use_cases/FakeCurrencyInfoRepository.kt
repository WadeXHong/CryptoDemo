package com.bardxhong.crypto.domain.use_cases

import com.bardxhong.crypto.data.room.CurrencyInfoRoomEntity
import com.bardxhong.crypto.domain.repo.ICurrencyInfoRepository

class FakeCurrencyInfoRepository(private val fakeResult: Result<List<CurrencyInfoRoomEntity>>) :
    ICurrencyInfoRepository {
    override suspend fun getAllCurrencyInfo(): Result<List<CurrencyInfoRoomEntity>> {
        return fakeResult
    }
}
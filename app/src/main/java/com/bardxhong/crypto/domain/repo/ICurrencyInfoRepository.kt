package com.bardxhong.crypto.domain.repo

import com.bardxhong.crypto.data.room.CurrencyInfoRoomEntity

interface ICurrencyInfoRepository {
    suspend fun getAllCurrencyInfo(): Result<List<CurrencyInfoRoomEntity>>
}

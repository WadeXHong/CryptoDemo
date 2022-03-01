package com.bardxhong.crypto.data.repo

import com.bardxhong.crypto.data.CurrencyInfoRemoteEntity
import com.bardxhong.crypto.data.remote.CurrencyService
import com.bardxhong.crypto.data.remote.toResult
import com.bardxhong.crypto.data.room.CurrencyInfoDatabase
import com.bardxhong.crypto.data.room.CurrencyInfoRoomEntity
import com.bardxhong.crypto.domain.repo.ICurrencyInfoRepository
import com.bardxhong.crypto.shared.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CurrencyInfoRepositoryImpl(
    // TODO inject by hilt
    private val service: CurrencyService = RetrofitProvider.service,
    private val roomDatabase: CurrencyInfoDatabase = CurrencyInfoDatabase.getInstance()
) : ICurrencyInfoRepository {
    override suspend fun getAllCurrencyInfo(): Result<List<CurrencyInfoRoomEntity>> =
        withContext(Dispatchers.IO) {
            toResult { getCurrencyInfoList() }.invoke()
                .map { remoteEntities ->
                    val roomEntities = remoteEntities.map { CurrencyInfoRoomEntity(it) }
                    roomDatabase.currencyInfoDao().insertAll(roomEntities)
                    return@map roomEntities
                }
                .recover {
                    backupList()
                }
        }

    private suspend fun getCurrencyInfoList(
        name: String? = null,
        page: Int? = null,
    ): Response<List<CurrencyInfoRemoteEntity>> {
        return service.getCurrencyInfoList(name, page)
    }

    private suspend fun backupList(): List<CurrencyInfoRoomEntity> = withContext(Dispatchers.IO) {
        roomDatabase.currencyInfoDao().getAll()
    }

}
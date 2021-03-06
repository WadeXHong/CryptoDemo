package com.bardxhong.crypto.data.repo

import com.bardxhong.crypto.data.CurrencyInfoRemoteEntity
import com.bardxhong.crypto.data.remote.CurrencyService
import com.bardxhong.crypto.data.remote.toResult
import com.bardxhong.crypto.data.room.CurrencyInfoDao
import com.bardxhong.crypto.data.room.CurrencyInfoRoomEntity
import com.bardxhong.crypto.domain.repo.ICurrencyInfoRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CurrencyInfoRepositoryImpl @Inject constructor(
    private val service: CurrencyService,
    private val currencyInfoDao: CurrencyInfoDao
) : ICurrencyInfoRepository {
    override suspend fun getAllCurrencyInfo(): Result<List<CurrencyInfoRoomEntity>> =
        withContext(Dispatchers.IO) {
            toResult { getCurrencyInfoList() }.invoke()
                .map { remoteEntities ->
                    val roomEntities = remoteEntities.map { CurrencyInfoRoomEntity(it) }
                    currencyInfoDao.insertAll(roomEntities)
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
        currencyInfoDao.getAll()
    }

}
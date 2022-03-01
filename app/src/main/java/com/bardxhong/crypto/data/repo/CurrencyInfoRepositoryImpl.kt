package com.bardxhong.crypto.data.repo

import com.bardxhong.crypto.CryptoApplication
import com.bardxhong.crypto.R
import com.bardxhong.crypto.data.CurrencyInfoRemoteEntity
import com.bardxhong.crypto.data.remote.CurrencyService
import com.bardxhong.crypto.data.remote.toResult
import com.bardxhong.crypto.data.room.CurrencyInfoDatabase
import com.bardxhong.crypto.data.room.CurrencyInfoRoomEntity
import com.bardxhong.crypto.domain.repo.ICurrencyInfoRepository
import com.bardxhong.crypto.shared.MoshiUtil
import com.bardxhong.crypto.shared.RetrofitProvider
import com.squareup.moshi.Types
import java.lang.reflect.Type
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

    @Deprecated("directly use roomDatabase and move loading fake logic to interceptor or other files")
    private suspend fun backupList(): List<CurrencyInfoRoomEntity> = withContext(Dispatchers.IO) {
//        roomDatabase.currencyInfoDao().getAll()
        val string = CryptoApplication.appContext.resources
            .openRawResource(R.raw.currency_list_info)
            .bufferedReader()
            .use { it.readText() }

        val listType: Type = Types.newParameterizedType(
            MutableList::class.java,
            CurrencyInfoRemoteEntity::class.java
        )

        MoshiUtil.moshi
            .adapter<List<CurrencyInfoRemoteEntity>>(listType)
            .fromJson(string)
            ?.map {
                CurrencyInfoRoomEntity(it)
            } ?: emptyList()
    }

    private suspend fun getCurrencyInfoList(
        name: String? = null,
        page: Int? = null,
    ): Response<List<CurrencyInfoRemoteEntity>> {
        return service.getCurrencyInfoList(name, page)
    }
}
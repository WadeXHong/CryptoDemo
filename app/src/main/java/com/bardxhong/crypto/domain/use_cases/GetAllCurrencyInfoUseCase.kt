package com.bardxhong.crypto.domain.use_cases

import com.bardxhong.crypto.data.repo.CurrencyInfoRepositoryImpl
import com.bardxhong.crypto.domain.repo.ICurrencyInfoRepository
import com.bardxhong.crypto.domain.view_entities.CurrencyInfoViewEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllCurrencyInfoUseCase(
    private val repo: ICurrencyInfoRepository = CurrencyInfoRepositoryImpl()
) {
    suspend operator fun invoke(): Flow<List<CurrencyInfoViewEntity>> {
        return flow {
            repo.getAllCurrencyInfo()
                .onSuccess { roomEntity ->
                    emit(roomEntity.map { CurrencyInfoViewEntity(it) })
                }
                .onFailure {
                    throw it
                }
        }.flowOn(Dispatchers.IO)
    }
}
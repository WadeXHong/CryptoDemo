package com.bardxhong.crypto.domain.use_cases

import com.bardxhong.crypto.domain.repo.ICurrencyInfoRepository
import com.bardxhong.crypto.domain.viewEntity.CurrencyInfoViewEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllCurrencyInfoUseCase(private val repo: ICurrencyInfoRepository) {
    suspend operator fun invoke(): Flow<List<CurrencyInfoViewEntity>> {
        return flow {
            repo.getAllCurrencyInfo()
                .onSuccess { roomEntity ->
                    emit(roomEntity.map { CurrencyInfoViewEntity(it) })
                }
                .onFailure {
                    throw it
                }
        }
    }
}
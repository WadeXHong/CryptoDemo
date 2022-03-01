package com.bardxhong.crypto.domain.use_cases

import com.bardxhong.crypto.domain.view_entities.CurrencyInfoViewEntity
import com.bardxhong.crypto.shared.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SortByOrderUseCase {
    suspend operator fun invoke(
        list: List<CurrencyInfoViewEntity>,
        order: Order
    ): Flow<List<CurrencyInfoViewEntity>> =
        flow {
            emit(
                when (order) {
                    Order.UNSPECIFIED -> list
                    Order.ASCENDING -> list.sortedBy { it.name }
                    Order.DESCENDING -> list.sortedByDescending { it.name }
                }
            )
        }.flowOn(Dispatchers.IO)
}
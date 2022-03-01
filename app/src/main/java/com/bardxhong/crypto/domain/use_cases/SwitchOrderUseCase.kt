package com.bardxhong.crypto.domain.use_cases

import com.bardxhong.crypto.shared.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SwitchOrderUseCase {
    operator fun invoke(order: Order): Flow<Order> = flow {
        emit(
            when (order) {
                Order.UNSPECIFIED,
                Order.DESCENDING -> Order.ASCENDING
                Order.ASCENDING -> Order.DESCENDING
            }
        )
    }
}
package com.bardxhong.crypto.domain.use_cases

import com.bardxhong.crypto.shared.Order

class SwitchOrderUseCase {
    operator fun invoke(order: Order): Order {
        return when (order) {
            Order.UNSPECIFIED,
            Order.DESCENDING -> Order.ASCENDING
            Order.ASCENDING -> Order.DESCENDING
        }
    }
}
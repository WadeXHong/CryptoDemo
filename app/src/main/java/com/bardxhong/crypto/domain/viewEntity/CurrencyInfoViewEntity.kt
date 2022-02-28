package com.bardxhong.crypto.domain.viewEntity

import com.bardxhong.crypto.data.room.CurrencyInfoRoomEntity

data class CurrencyInfoViewEntity(
    val id: String,
    val name: String,
    val symbol: String,
) {
    constructor(roomEntity: CurrencyInfoRoomEntity) : this(
        id = roomEntity.id,
        name = roomEntity.name,
        symbol = roomEntity.symbol
    )
}
package com.bardxhong.crypto.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bardxhong.crypto.data.CurrencyInfoRemoteEntity
import com.bardxhong.crypto.data.room.RoomConstant.TABLE_NAME_CURRENCY_INFO

@Entity(tableName = TABLE_NAME_CURRENCY_INFO)
data class CurrencyInfoRoomEntity(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
) {
    constructor(remoteEntity: CurrencyInfoRemoteEntity) : this(
        id = remoteEntity.id,
        name = remoteEntity.name,
        symbol = remoteEntity.symbol
    )
}

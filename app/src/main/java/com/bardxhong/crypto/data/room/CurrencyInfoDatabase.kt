package com.bardxhong.crypto.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bardxhong.crypto.data.room.RoomConstant.CURRENCY_INFO_DATABASE_VERSION

@Database(
    entities = [CurrencyInfoRoomEntity::class],
    version = CURRENCY_INFO_DATABASE_VERSION
)
abstract class CurrencyInfoDatabase : RoomDatabase() {
    abstract fun currencyInfoDao(): CurrencyInfoDao
}
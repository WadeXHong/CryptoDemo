package com.bardxhong.crypto.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bardxhong.crypto.data.room.RoomConstant.TABLE_NAME_CURRENCY_INFO

@Dao
interface CurrencyInfoDao {
    @Query("SELECT * FROM $TABLE_NAME_CURRENCY_INFO")
    suspend fun getAll(): List<CurrencyInfoRoomEntity>

    @Query("SELECT * FROM $TABLE_NAME_CURRENCY_INFO WHERE id = :id")
    suspend fun getCurrencyInfoById(id: String): CurrencyInfoRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyList: List<CurrencyInfoRoomEntity>)
}
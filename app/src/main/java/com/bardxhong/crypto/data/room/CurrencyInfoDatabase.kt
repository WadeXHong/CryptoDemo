package com.bardxhong.crypto.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bardxhong.crypto.CryptoApplication
import com.bardxhong.crypto.data.room.RoomConstant.CURRENCY_INFO_DATABASE_VERSION

@Database(
    entities = [CurrencyInfoRoomEntity::class],
    version = CURRENCY_INFO_DATABASE_VERSION
)
abstract class CurrencyInfoDatabase : RoomDatabase() {
    abstract fun currencyInfoDao(): CurrencyInfoDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyInfoDatabase? = null

        // TODO use hilt singleton to inject application
        fun getInstance(context: Context = CryptoApplication.appContext): CurrencyInfoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: createInstance(context).also { INSTANCE = it }
            }

        private fun createInstance(context: Context): CurrencyInfoDatabase =
            Room.databaseBuilder(
                context,
                CurrencyInfoDatabase::class.java,
                RoomConstant.DATABASE_NAME
            ).build()
    }
}
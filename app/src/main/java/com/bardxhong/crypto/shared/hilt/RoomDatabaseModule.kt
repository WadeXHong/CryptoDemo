package com.bardxhong.crypto.shared.hilt

import android.app.Application
import androidx.room.Room
import com.bardxhong.crypto.data.room.CurrencyInfoDao
import com.bardxhong.crypto.data.room.CurrencyInfoDatabase
import com.bardxhong.crypto.data.room.RoomConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {
    @Provides
    @Singleton
    fun provideCurrencyInfoDatabase(context: Application): CurrencyInfoDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyInfoDatabase::class.java,
            RoomConstant.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(database: CurrencyInfoDatabase): CurrencyInfoDao {
        return database.currencyInfoDao()
    }
}
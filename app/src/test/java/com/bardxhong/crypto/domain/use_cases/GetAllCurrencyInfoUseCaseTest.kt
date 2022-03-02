package com.bardxhong.crypto.domain.use_cases

import com.bardxhong.crypto.data.room.CurrencyInfoRoomEntity
import com.bardxhong.crypto.domain.view_entities.CurrencyInfoViewEntity
import com.bardxhong.crypto.shared.FakeException
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertArrayEquals
import org.junit.Test

class GetAllCurrencyInfoUseCaseTest {

    @Test
    fun `test use case map room entities to view entities`() = runTest {
        // 1. Arrange
        val fakeRoomEntity = listOf(
            CurrencyInfoRoomEntity(
                id = "ETH",
                name = "Ethereum",
                symbol = "ETH",
            ),
            CurrencyInfoRoomEntity(
                id = "BTC",
                name = "Bitcoin",
                symbol = "BTC",
            ),
            CurrencyInfoRoomEntity(
                id = "XRP",
                name = "XRP",
                symbol = "XRP",
            )
        )
        val repo = FakeCurrencyInfoRepository(Result.success(fakeRoomEntity))
        val expected = listOf(
            CurrencyInfoViewEntity(
                id = "ETH",
                name = "Ethereum",
                symbol = "ETH",
            ),
            CurrencyInfoViewEntity(
                id = "BTC",
                name = "Bitcoin",
                symbol = "BTC",
            ),
            CurrencyInfoViewEntity(
                id = "XRP",
                name = "XRP",
                symbol = "XRP",
            ),
        )
        // 2. Act
        val result = GetAllCurrencyInfoUseCase(repo).invoke().single()
        // 3. Assert
        assertArrayEquals(expected.toTypedArray(), result.toTypedArray())
    }

    @Test(expected = FakeException::class)
    fun `test result throw when use case failed`() = runTest {
        // 1. Arrange
        val repo = FakeCurrencyInfoRepository(Result.failure(FakeException))
        // 2. Act
        GetAllCurrencyInfoUseCase(repo).invoke().single()
    }
}
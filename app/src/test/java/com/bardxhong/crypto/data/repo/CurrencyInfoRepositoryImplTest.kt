package com.bardxhong.crypto.data.repo

import com.bardxhong.crypto.data.CurrencyInfoRemoteEntity
import com.bardxhong.crypto.data.remote.CurrencyService
import com.bardxhong.crypto.data.room.CurrencyInfoDao
import com.bardxhong.crypto.data.room.CurrencyInfoRoomEntity
import com.bardxhong.crypto.domain.repo.ICurrencyInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class CurrencyInfoRepositoryImplTest {

    @MockK
    private lateinit var service: CurrencyService

    @MockK
    private lateinit var dao: CurrencyInfoDao

    private lateinit var repositoryImpl: ICurrencyInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        repositoryImpl = CurrencyInfoRepositoryImpl(service, dao)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test get remote source success will insert to dao`() = runTest {
        // 1. Arrange
        coEvery {
            service.getCurrencyInfoList(any(), any())
        } returns Response.success(mockk(relaxed = true))

        // 2. Act
        val result = repositoryImpl.getAllCurrencyInfo().getOrThrow()

        // 3. Assert
        coVerify(exactly = 1) { service.getCurrencyInfoList(any(), any()) }
        coVerify(exactly = 1) { dao.insertAll(any()) }
        coVerify(exactly = 0) { dao.getAll() }
    }

    @Test
    fun `test get remote source success will get backup from dao`() = runTest {
        // 1. Arrange
        coEvery {
            service.getCurrencyInfoList(any(), any())
        } returns Response.error(500, mockk(relaxed = true))

        // 2. Act
        val result = repositoryImpl.getAllCurrencyInfo().getOrThrow()

        // 3. Assert
        coVerify(exactly = 1) { service.getCurrencyInfoList(any(), any()) }
        coVerify(exactly = 0) { dao.insertAll(any()) }
        coVerify(exactly = 1) { dao.getAll() }
    }

    /**
     * This test might be unnecessary for now or need to adjust assertion content according to
     * what logic do we care.
     */
    @Test
    fun `test get remote entities transfer to room entities as expect`() = runTest {
        // 1. Arrange
        val fakeRemoteEntity = listOf(
            CurrencyInfoRemoteEntity(
                id = "ETH",
                name = "Ethereum",
                symbol = "ETH",
            ),
            CurrencyInfoRemoteEntity(
                id = "BTC",
                name = "Bitcoin",
                symbol = "BTC",
            ),
            CurrencyInfoRemoteEntity(
                id = "XRP",
                name = "XRP",
                symbol = "XRP",
            ),
        )

        coEvery {
            service.getCurrencyInfoList(any(), any())
        } returns Response.success(fakeRemoteEntity)

        // 2. Act
        val result = repositoryImpl.getAllCurrencyInfo().getOrThrow()

        // 3. Assert
        assertArrayEquals(
            fakeRemoteEntity.map { CurrencyInfoRoomEntity(it) }.toTypedArray(),
            result.toTypedArray()
        )
    }
}
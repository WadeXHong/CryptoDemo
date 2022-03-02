package com.bardxhong.crypto.domain.use_cases

import com.bardxhong.crypto.domain.view_entities.CurrencyInfoViewEntity
import com.bardxhong.crypto.shared.Order
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertArrayEquals
import org.junit.Test


class SortByOrderUseCaseTest {

    private val ENTITY_AA = CurrencyInfoViewEntity(
        id = "AA",
        name = "AA",
        symbol = "AA",
    )

    private val ENTITY_AB = CurrencyInfoViewEntity(
        id = "AB",
        name = "AB",
        symbol = "AB",
    )

    private val ENTITY_BA = CurrencyInfoViewEntity(
        id = "BA",
        name = "BA",
        symbol = "BA",
    )

    private val ENTITY_BB = CurrencyInfoViewEntity(
        id = "BB",
        name = "BB",
        symbol = "BB",
    )

    @Test
    fun `test sort by ASCENDING works as expected`() = runTest {
        // 1. Arrange
        val inputList = createShuffledCurrencyInfoViewEntity()
        // 2. Act
        val result = SortByOrderUseCase().invoke(inputList, Order.ASCENDING).single()
        val expected = listOf(ENTITY_AA, ENTITY_AB, ENTITY_BA, ENTITY_BB)
        // 3. Assert
        assertArrayEquals(expected.toTypedArray(), result.toTypedArray())
    }

    @Test
    fun `test sort by DESCENDING works as expected`() = runTest {
        // 1. Arrange
        val inputList = createShuffledCurrencyInfoViewEntity()
        // 2. Act
        val result = SortByOrderUseCase().invoke(inputList, Order.DESCENDING).single()
        val expected = listOf(ENTITY_BB, ENTITY_BA, ENTITY_AB, ENTITY_AA)
        // 3. Assert
        assertArrayEquals(expected.toTypedArray(), result.toTypedArray())
    }

    @Test
    fun `test sort by UNSPECIFIED works as expected`() = runTest {
        // 1. Arrange
        val inputList = createShuffledCurrencyInfoViewEntity()
        // 2. Act
        val result = SortByOrderUseCase().invoke(inputList, Order.UNSPECIFIED).single()
        // 3. Assert
        assertArrayEquals(inputList.toTypedArray(), result.toTypedArray())
    }

    private fun createShuffledCurrencyInfoViewEntity(): List<CurrencyInfoViewEntity> {
        return listOf(
            ENTITY_AA,
            ENTITY_AB,
            ENTITY_BA,
            ENTITY_BB
        ).shuffled()
    }
}
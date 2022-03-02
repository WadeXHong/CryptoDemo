package com.bardxhong.crypto.domain.use_cases

import com.bardxhong.crypto.shared.Order
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SwitchOrderUseCaseTest {

    @Test
    fun `check use case returns ASCENDING when input is UNSPECIFIED`() = runTest {
        val result = SwitchOrderUseCase().invoke(Order.UNSPECIFIED).first()
        assertEquals(Order.ASCENDING, result)
    }

    @Test
    fun `check use case returns ASCENDING when input is DESCENDING`() = runTest {
        val result = SwitchOrderUseCase().invoke(Order.DESCENDING).first()
        assertEquals(Order.ASCENDING, result)
    }

    @Test
    fun `check use case returns DESCENDING when input is ASCENDING`() = runTest {
        val result = SwitchOrderUseCase().invoke(Order.ASCENDING).first()
        assertEquals(Order.DESCENDING, result)
    }
}
package com.bardxhong.crypto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bardxhong.crypto.domain.use_cases.GetAllCurrencyInfoUseCase
import com.bardxhong.crypto.domain.use_cases.SortByOrderUseCase
import com.bardxhong.crypto.domain.use_cases.SwitchOrderUseCase
import com.bardxhong.crypto.domain.view_entities.CurrencyInfoViewEntity
import com.bardxhong.crypto.shared.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    private val getAllCurrencyInfoUseCase: GetAllCurrencyInfoUseCase = GetAllCurrencyInfoUseCase(),
    private val switchOrderUseCase: SwitchOrderUseCase = SwitchOrderUseCase(),
    private val sortByOrderUseCase: SortByOrderUseCase = SortByOrderUseCase()
) : ViewModel() {

    private val _viewEntityStateFlow = MutableStateFlow<List<CurrencyInfoViewEntity>>(emptyList())
    val viewEntityStateFlow: StateFlow<List<CurrencyInfoViewEntity>> = _viewEntityStateFlow

    private val _orderStateFlow = MutableStateFlow<Order>(Order.UNSPECIFIED)
    val orderStateFlow: StateFlow<Order> = _orderStateFlow

    private val isInitialState
        get() = viewEntityStateFlow.value.isEmpty() && orderStateFlow.value == Order.UNSPECIFIED

    private var loadingDataJob: Job? = null

    private fun updateEntityAction(entities: List<CurrencyInfoViewEntity>) {
        _viewEntityStateFlow.value = entities
    }

    private fun updateOrderAction(order: Order) {
        _orderStateFlow.value = order
    }

    fun getAllCurrency() {
        loadingDataJob?.cancel()
        loadingDataJob = viewModelScope.launch(Dispatchers.IO) {
            getAllCurrencyUseCase(orderStateFlow.value).collect()
        }
    }

    fun changeOrderAndGetAllCurrency() {
        loadingDataJob?.cancel()
        loadingDataJob = viewModelScope.launch {
            switchOrderFlow(orderStateFlow.value)
                .flatMapConcat { order ->
                    val viewEntities = viewEntityStateFlow.value
                    when {
                        isInitialState -> emptyFlow()
                        viewEntities.isEmpty() -> getAllCurrencyUseCase(order)
                        else -> sortByOrderFlow(viewEntities, order)
                    }
                }
                .collect()
        }
    }

    private suspend fun getAllCurrencyUseCase(order: Order): Flow<List<CurrencyInfoViewEntity>> =
        getAllCurrencyInfoUseCase()
            .flatMapConcat {
                sortByOrderFlow(it, order)
            }

    private suspend fun sortByOrderFlow(
        entities: List<CurrencyInfoViewEntity>,
        order: Order
    ): Flow<List<CurrencyInfoViewEntity>> =
        sortByOrderUseCase(entities, order).onEach(::updateEntityAction)


    private fun switchOrderFlow(order: Order): Flow<Order> =
        switchOrderUseCase(order).onEach(::updateOrderAction)

}